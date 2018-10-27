package commands.untyped

import cats.data.Validated
import event.Event
import jto.validation.{Path, ValidationError}
import play.api.libs.json._
import states.State

trait Command[E <: Event, S <: State] {
  def validate(id : String, persisted: Option[S]): Validated[Command.Error, E] = ???
}

object Command {
  type Error = String
  val BoardNotFound  =Validated.invalid("Board not found")
  val TicketNotFound  =Validated.invalid("Ticket not found")

}







