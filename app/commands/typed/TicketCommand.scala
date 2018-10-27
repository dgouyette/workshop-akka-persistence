package commands.typed

import akka.actor.typed.ActorRef
import cats.data.Validated
import event.TicketEvent
import states.Ticket
import states.Ticket.{Done, InProgress, Todo}

sealed trait TicketCommand extends Command[TicketEvent, states.Ticket]

object TicketCommand {
  case class UpdateStatusTodo(replyTo : ActorRef[Either[Command.Error, TicketEvent]]) extends TicketCommand
  case class UpdateStatusInProgress(replyTo : ActorRef[Either[Command.Error, TicketEvent]]) extends TicketCommand
  case class UpdateStatusDone(replyTo : ActorRef[Either[Command.Error, TicketEvent]]) extends TicketCommand
  case class Create(boardId : String, title: String, description: String, replyTo : ActorRef[Either[Command.Error, TicketEvent]]) extends TicketCommand

}