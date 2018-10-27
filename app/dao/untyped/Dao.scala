package dao.untyped

import akka.actor.Props
import anorm.{SqlParser, postgresql}
import play.api.db.Database
import play.api.libs.json.JsObject

object Dao {
  import postgresql._
  val persistenceIdAndJson = SqlParser.str("persistenceId") ~ SqlParser.get[JsObject]("json")

  trait BoardDaoTag
  trait TicketDaoTag

  def boardDaoProps(implicit database: Database) = Props(new BoardDao(database))

  def ticketDaoProps(implicit database: Database) = Props(new TicketDao(database))

}
