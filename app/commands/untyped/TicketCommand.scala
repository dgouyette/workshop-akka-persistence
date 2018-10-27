package commands.untyped

import event.TicketEvent

sealed trait TicketCommand extends Command[TicketEvent, states.Ticket]

object TicketCommand {

  case object UpdateStatusTodo extends TicketCommand

  case object UpdateStatusInProgress extends TicketCommand

  case object UpdateStatusDone extends TicketCommand

  case class Create(boardId: String, title: String, description: String) extends TicketCommand
}