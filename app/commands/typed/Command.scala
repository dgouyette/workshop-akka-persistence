package commands.typed

import akka.actor.typed.ActorRef
import cats.data.Validated
import event.Event
import jto.validation.{Path, ValidationError}
import states.State

trait Command[E <: Event, S <: State] {
  val replyTo :  ActorRef[Either[String, E]]
  def validate(id : String, persisted: Option[S]): Validated[Command.Error, E] = ???
}


object Command {
  type Error = String
  val BoardNotFound  =Validated.invalid("Board not found")
  val TicketNotFound  =Validated.invalid("Ticket not found")
}