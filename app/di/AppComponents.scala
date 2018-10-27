package di

import akka.{actor => untyped}
import com.softwaremill.macwire._
import com.softwaremill.tagging._
import controllers._
import controllers.monitoring.MonitoringController
import dao.untyped.Dao.{BoardDaoTag, TicketDaoTag}
import event.LookupBusImpl
import play.api.ApplicationLoader.Context
import play.api.db._
import play.api.http.HttpConfiguration
import play.api.i18n.I18nComponents
import play.api.libs.parsers.DefaultBPUBIBodyParsers
import play.api.mvc.EssentialFilter
import play.api.routing.Router
import play.api.{BuiltInComponentsFromContext, LoggerConfigurator}
import router.Routes
import serialization.EventJsonEncoder


class AppComponents(context: Context) extends BuiltInComponentsFromContext(context)
  with AssetsComponents
  with I18nComponents
  with play.filters.HttpFiltersComponents
  with DBComponents
  with BoneCPComponents {


  // set up logger
  LoggerConfigurator(context.environment.classLoader).foreach {
    _.configure(context.environment, context.initialConfiguration, Map.empty)
  }

  lazy val router: Router = {
    // add the prefix string in local scope for the Routes constructor
    val prefix: String = "/"
    wire[Routes]
  }
  override def httpFilters: Seq[EssentialFilter] = Seq()

  implicit val as = this.actorSystem

  implicit lazy val assetsBuilder = new controllers.AssetsBuilder(httpErrorHandler, assetsMetadata)


  implicit val database: Database = dbApi.database("default")
  implicit val boardDao: @@[untyped.ActorRef, BoardDaoTag] =    actorSystem.actorOf(dao.untyped.Dao.boardDaoProps).taggedWith[BoardDaoTag]
  implicit val ticketDao: @@[untyped.ActorRef, TicketDaoTag] = actorSystem.actorOf(dao.untyped.Dao.ticketDaoProps).taggedWith[TicketDaoTag]

  import akka.actor.typed.scaladsl.adapter._
  val  boardDaoTyped: akka.actor.typed.ActorRef[dao.typed.BoardDao.Protocol] =  actorSystem.spawn(dao.typed.BoardDao.behavior, "boardDaoActor")
  val  ticketDaoTyped: akka.actor.typed.ActorRef[dao.typed.TicketDao.Protocol] =  actorSystem.spawn(dao.typed.TicketDao.behavior, "ticketDaoActor")

  implicit val eventBus: LookupBusImpl = wire[LookupBusImpl]
  implicit val eventJsonEncoder = wire[EventJsonEncoder]

  lazy val untypedBoardController = wire[controllers.untyped.BoardController]
  lazy val monitoringController = wire[MonitoringController]
  lazy val untypedTicketController = wire[controllers.untyped.TicketController]

  lazy val typedBoardController = wire[controllers.typed.BoardController]
  lazy val typedTicketController = wire[controllers.typed.TicketController]

  implicit def parser: DefaultBPUBIBodyParsers = new DefaultBPUBIBodyParsers(HttpConfiguration.fromConfiguration(configuration, environment).parser, materializer, tempFileCreator)


}

