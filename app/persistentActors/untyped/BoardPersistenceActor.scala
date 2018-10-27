package persistentActors.untyped

import akka.actor.{ActorSystem, Props}
import commands.untyped.{BoardCommand, TicketCommand}
import event.{BoardEvent, LookupBusImpl, TicketEvent}
import eventHandlers.{BoardEventHandler, EventHandler, TicketEventHandler}
import states.{Board, Ticket}

object BoardPersistenceActor {
  def props(cleanId: String)(implicit actorSystem: ActorSystem, eventBus : LookupBusImpl) = Props(new BoardPersistenceActor(cleanId, eventBus))

}
class BoardPersistenceActor(id : String, val eventBus : LookupBusImpl)(implicit actorSystem: ActorSystem) extends DefaultPersistentActor[Board, BoardEvent, BoardCommand] {
  override def persistenceId: String = id
  override val eventHandler: EventHandler[Board, BoardEvent] = BoardEventHandler
  override implicit def as: ActorSystem = actorSystem

}


object TicketPersistenceActor {
  def props(cleanId: String)(implicit actorSystem: ActorSystem, eventBus : LookupBusImpl) = Props(new TicketPersistenceActor(cleanId, eventBus))
}
class TicketPersistenceActor(id : String, val eventBus : LookupBusImpl)(implicit actorSystem: ActorSystem) extends DefaultPersistentActor[states.Ticket, TicketEvent, TicketCommand] {
  override def persistenceId: String = id
  override val eventHandler: EventHandler[Ticket, TicketEvent] = TicketEventHandler
  override implicit def as: ActorSystem = actorSystem

}
