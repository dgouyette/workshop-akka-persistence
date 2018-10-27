package dao.typed

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import anorm._
import play.api.Logger
import play.api.db.Database
import play.api.libs.json.{JsObject, Json}

object BoardDao {

  sealed trait Protocol
  case class FindAll(replyTo: ActorRef[Seq[JsObject]]) extends Protocol
  case class FindById(id: String, replyTo: ActorRef[Option[JsObject]]) extends Protocol
  case class DeleteSnapshot(persistenceId : String, sequenceNr : Long) extends Protocol

  def behavior(implicit database: Database): Behavior[Protocol] = Behaviors.receiveMessage {
    case FindAll(replyTo) =>
      replyTo ! database.withConnection { implicit c =>
        SQL(s"select persistenceId,json from snapshot WHERE (json ->> 'type') = 'Board'")
          .as(Dao.persistenceIdAndJson.*).map { case id ~ json => Json.obj("id" -> id) ++ json }
      }
      Behaviors.same

    case FindById(persistenceId, replyTo) =>
      replyTo ! database.withConnection { implicit c =>
        SQL(s"select persistenceId,json from snapshot WHERE persistenceId = '$persistenceId' and (json ->> 'type') = 'Board'")
          .as(Dao.persistenceIdAndJson.singleOpt).map { case id ~ json => Json.obj("id" -> id) ++ json }
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

