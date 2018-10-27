package controllers.untyped

import akka.actor.{ActorRef, ActorSystem}
import akka.stream.Materializer
import akka.util.Timeout
import com.softwaremill.tagging._
import dao.untyped.Dao.BoardDaoTag
import event.LookupBusImpl
import play.api.Configuration
import play.api.libs.parsers.DefaultBP
import play.api.mvc._
import serialization.EventJsonEncoder

import scala.concurrent.duration._
import scala.language.postfixOps

class BoardController(cc: ControllerComponents,
                      val parsers: DefaultBP,
                      val configuration: Configuration,
                      val boardDao: ActorRef @@ BoardDaoTag,
                      val eventJsonEncoder: EventJsonEncoder
                     )(implicit val actorSystem: ActorSystem, mat: Materializer, evenBus: LookupBusImpl) extends AbstractController(cc) {

  implicit val timeout: Timeout = Timeout(2 seconds)

  def boardPersistentActor(idPack: String): ActorRef = ???



  def create= TODO

  def show(id: String) = TODO
  def list = TODO

  /** Affiche la partie front **/
  def index = Action {
    implicit req =>
      Ok(views.html.index())
  }
  def events=TODO
}
