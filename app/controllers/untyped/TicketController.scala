package controllers.untyped

import akka.stream.Materializer
import akka.util.Timeout
import akka.{actor => untyped}
import com.softwaremill.tagging._
import dao.untyped.Dao.TicketDaoTag
import event.LookupBusImpl
import play.api.Configuration
import play.api.libs.parsers.DefaultBP
import play.api.mvc.{AbstractController, ControllerComponents}
import serialization.EventJsonEncoder

import scala.concurrent.duration._
import scala.language.postfixOps

class TicketController(cc: ControllerComponents,
                       val parsers: DefaultBP,
                       val configuration: Configuration,
                       val ticketDao: untyped.ActorRef @@ TicketDaoTag,
                       val eventJsonEncoder: EventJsonEncoder

                      )(implicit val actorSystem: untyped.ActorSystem, mat: Materializer, evenBus: LookupBusImpl) extends AbstractController(cc) {

  implicit val timeout: Timeout = Timeout(2 seconds)

  def ticketPersistentActor(idTicket: String): untyped.ActorRef = ???
  def list(boardId: String) =TODO
  def updateStatusTodo(boardId: String, ticketId: String) = TODO
  def updateStatusInProgress(boardId: String, ticketId: String) = TODO
  def updateStatusDone(boardId: String, ticketId: String) = TODO
  def create(boardId: String) = TODO
  def events(boardId: String) = TODO
}