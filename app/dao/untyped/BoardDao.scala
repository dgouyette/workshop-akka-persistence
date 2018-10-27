package dao.untyped

import akka.actor.{Actor, ActorLogging}
import anorm._
import play.api.db.Database
import play.api.libs.json.Json

object BoardDao{
  case object FindAll
  case class FindById(id : String)
}
class BoardDao(database: Database) extends Actor  with ActorLogging {
  override def receive: Receive = {
    case BoardDao.FindById(persistenceId) => database.withConnection { implicit c =>
      sender() ! SQL(
        s"select persistenceId,json from snapshot WHERE persistenceId = '$persistenceId' and (json ->> 'type') = 'Board'"
      ).as(Dao.persistenceIdAndJson.singleOpt).map { case id ~ json => Json.obj("id" -> id) ++ json }
    }
    case BoardDao.FindAll => {
      database.withConnection { implicit c =>
        sender() ! SQL(
          s"select persistenceId,json from snapshot WHERE (json ->> 'type') = 'Board'"
        ).as(Dao.persistenceIdAndJson.*).map { case id ~ json => Json.obj("id" -> id) ++ json }
      }
    }
    case other => log.warning(s"received $other")
  }
}