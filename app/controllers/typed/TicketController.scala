package controllers.typed

import akka.actor.Scheduler
import akka.actor.typed.ActorRef
import akka.util.Timeout
import akka.{actor => untyped}
import commands.typed.TicketCommand
import dao.typed.TicketDao
import play.api.Configuration
import play.api.db.Database
import play.api.libs.parsers.DefaultBP
import play.api.mvc.{AbstractController, ControllerComponents}
import serialization.EventJsonEncoder

import scala.concurrent.duration._
import scala.language.postfixOps

class TicketController(cc: ControllerComponents,
                       val parsers: DefaultBP,
                       val configuration: Configuration,
                       val ticketDao: ActorRef[TicketDao.Protocol],
                       val eventJsonEncoder: EventJsonEncoder
                      )(implicit actorSystem: untyped.ActorSystem, database: Database) extends AbstractController(cc) {
  implicit val scheduler: Scheduler = actorSystem.scheduler
  implicit val timeout: Timeout = Timeout(10 seconds)

  def ticketPersistentActor(ticketId: String): ActorRef[TicketCommand] = ???

  def list(boardId: String) = TODO
  def updateStatusTodo(boardId: String, ticketId: String) =TODO
  def updateStatusInProgress(boardId: String, ticketId: String) = TODO
  def updateStatusDone(boardId: String, ticketId: String) = TODO
  def create(boardId: String) = TODO
  def show = TODO
  def events(boardId: String) = TODO
}
