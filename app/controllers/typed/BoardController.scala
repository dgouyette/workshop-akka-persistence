package controllers.typed

import akka.actor.ActorSystem
import akka.actor.typed.ActorRef
import akka.util.Timeout
import commands.typed
import dao.typed.BoardDao
import play.api.Configuration
import play.api.db.Database
import play.api.libs.parsers.DefaultBP
import play.api.mvc.{AbstractController, ControllerComponents}
import serialization.EventJsonEncoder

import scala.concurrent.duration._
import scala.language.postfixOps

class BoardController(cc: ControllerComponents,
                      val parsers: DefaultBP,
                      val configuration: Configuration,
                      val boardDao: ActorRef[BoardDao.Protocol],
                      val eventJsonEncoder: EventJsonEncoder
                     )(implicit actorSystem : ActorSystem, database: Database) extends AbstractController(cc) {

  implicit val scheduler = actorSystem.scheduler
  implicit val timeout: Timeout = Timeout(10 seconds)




  def boardPersistentActor(boardId: String): ActorRef[typed.BoardCommand] = ???
  def create  = TODO
  def show(id : String) = TODO
  def list = TODO
  def events = TODO
}