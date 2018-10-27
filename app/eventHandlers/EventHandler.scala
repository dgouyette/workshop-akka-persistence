package eventHandlers

import event.{BoardEvent, Event, TicketEvent}
import states.{Board, Ticket}

trait EventHandler[State, E <: Event] {
  def handle(e: E)(s: Option[State]): Option[State]
}

object BoardEventHandler extends EventHandler[states.Board, event.BoardEvent] {
  override def handle(e: BoardEvent)(state: Option[Board]): Option[Board] = {
    ???
  }
}

object TicketEventHandler extends EventHandler[states.Ticket, event.TicketEvent] {

  override def handle(e: TicketEvent)(state: Option[states.Ticket]): Option[Ticket] = {
    ???
  }
}