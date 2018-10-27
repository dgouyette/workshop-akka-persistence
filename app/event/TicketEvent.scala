package event

import julienrf.json.derived
import play.api.libs.json._
import states.Ticket
import states.Ticket.Status

sealed trait TicketEvent extends Event {
  val boardId: String
}

object TicketEvent {
  implicit val ticketEventF: OFormat[TicketEvent] = derived.flat.oformat[TicketEvent]((__ \ "type").format[String])
  case class Created(id: String, boardId: String, title: String, description: String, status: Status = Ticket.Todo) extends TicketEvent
  case class StatusChanged(boardId: String, status: Status) extends TicketEvent
}