package dao.untyped


import akka.actor.{Actor, ActorLogging}
import anorm._
import play.api.db.Database
import play.api.libs.json.Json

object TicketDao {
  case class FindById(id : String)
  case class FindAllByBoardId(boardId: String)
}

class TicketDao(database: Database) extends Actor with ActorLogging {
  override def receive: Receive = {
    case TicketDao.FindById(persistenceId) => database.withConnection { implicit c =>
      sender() ! SQL(
        s"select persistenceId,json from snapshot WHERE persistenceId = '$persistenceId' and (json ->> 'type') = 'Ticket'"
      ).as(Dao.persistenceIdAndJson.singleOpt).map { case id ~ json => Json.obj("id" -> id) ++ json }
    }

    case TicketDao.FindAllByBoardId(boardId) => {
      database.withConnection { implicit c =>
        sender() ! SQL(
          s"select persistenceId,json from snapshot WHERE (json ->> 'type') = 'Ticket' and (json ->> 'boardId') = '$boardId'"
        ).as(Dao.persistenceIdAndJson.*).map { case id ~ json => Json.obj("id" -> id) ++ json }
      }
    }
    case other => log.warning(s"received $other")
  }

}