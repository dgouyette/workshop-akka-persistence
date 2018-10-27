package states

import julienrf.json.derived
import play.api.libs.json._

case class Ticket(status : Ticket.Status, boardId : String, title : String, description : String) extends State

object Ticket {
  sealed trait Status
  object Status {
    implicit val boardEventF: OFormat[Status] = derived.flat.oformat[Status]((__ \ "type").format[String])
  }
  implicit val format = Json.format[Ticket]
  case object Todo extends Status
  case object InProgress extends Status
  case object Done extends Status
}