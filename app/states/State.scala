package states

import julienrf.json.derived
import play.api.libs.json._
import states.Board.Running

trait State extends Serializable

case class Board(title : String, description : String, status : Board.Status = Running) extends State
object Board{
  sealed trait Status
  case object Running extends Status
  case object Archived extends Status
  implicit val statusF: OFormat[Status] = derived.flat.oformat[Board.Status]((__ \ "type").format[String])
  implicit val format: OFormat[Board] = Json.format[Board]
}
