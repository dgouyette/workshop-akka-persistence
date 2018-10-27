package dao.typed

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import anorm.{SQL, ~}
import play.api.Logger
import play.api.db.Database
import play.api.libs.json.{JsObject, Json}

object TicketDao {

  sealed trait Protocol
  case class FindById(id: String, replyTo: ActorRef[Option[JsObject]]) extends Protocol
  case class FindAllByBoardId(boardId: String, replyTo: ActorRef[Seq[JsObject]]) extends Protocol
  case class DeleteSnapshot(persistenceId : String, sequenceNr : Long) extends Protocol

  def behavior(implicit database: Database): Behavior[Protocol] = Behaviors.receiveMessage {
    case FindAllByBoardId(boardId, replyTo) =>
      database.withConnection { implicit c =>
        replyTo ! SQL(
          s"select persistenceId,json from snapshot WHERE (json ->> 'type') = 'Ticket' and (json ->> 'boardId') = '$boardId'"
        ).as(Dao.persistenceIdAndJson.*).map { case id ~ json => Json.obj("id" -> id) ++ json }
      }
      Behaviors.same

    case FindById(persistenceId, replyTo) =>
      database.withConnection { implicit c =>
        replyTo ! SQL(
          s"select persistenceId,json from snapshot WHERE persistenceId = '$persistenceId' and (json ->> 'type') = 'Ticket'"
        ).as(Dao.persistenceIdAndJson.singleOpt).map { case id ~ json => Json.obj("id" -> id) ++ json }
      }
      Behaviors.same

    case DeleteSnapshot(persistenceId, sequenceNr) =>
      database.withConnection { implicit c =>
        val count =  SQL(s"delete from snapshot WHERE persistenceId = '$persistenceId' and sequencenr <= $sequenceNr ").executeUpdate()
        Logger.info(s"deleted $count snapshots")
      }
      Behaviors.stopped
  }
}
