package controllers.monitoring

import akka.actor.ActorSystem
import akka.cluster.Member
import akka.stream.Materializer
import akka.util.Timeout
import event.LookupBusImpl
import play.api.Configuration
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.language.postfixOps
class MonitoringController(cc: ControllerComponents,
                           val configuration: Configuration,
                          )(implicit val actorSystem: ActorSystem, mat: Materializer, evenBus: LookupBusImpl) extends AbstractController(cc) {
  implicit val timeout = Timeout(5 seconds)

  def index = Action.async {
    implicit req =>
      val cluster = akka.cluster.Cluster(actorSystem)
      val members: Set[Member] = cluster.state.members
      Future.successful(Ok(Json.obj("members"->  members.map(_.toString()))))
  }
}