package event

import julienrf.json.derived
import play.api.libs.json._
import states.Board.Status

sealed trait BoardEvent extends Event

object BoardEvent {

  implicit val boardEventF: OFormat[BoardEvent] = derived.flat.oformat[BoardEvent]((__ \ "type").format[String])
  case class StatusChanged(status: Status) extends BoardEvent
  case class Created(id: String, title: String, description: String) extends BoardEvent

  case object Exit extends BoardEvent
}