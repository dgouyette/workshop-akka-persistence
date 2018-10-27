package commands.untyped

import event.TicketEvent
import org.scalatestplus.play.PlaySpec
import states.Ticket
import states.Ticket.{Done, InProgress, Todo}

class TicketCommandSpec extends PlaySpec {

  val existingTicketTodo = Some(Ticket(Todo, "board1", "title", "description"))
  val existingTicketInProgress = Some(Ticket(InProgress, "board1", "title", "description"))
  val existingTicketDone = Some(Ticket(Done, "board1", "title", "description"))

  "Create a ticket with a valid title and description" should {
    "return an  event" in {
      TicketCommand.Create("1234", "title", "description").validate("1234", None).toEither mustBe 'Right
    }
  }

  "Create a ticket with an empty title or description" should {
    "return an error message" in {
      TicketCommand.Create("1234", "", "description").validate("1234", None).toEither mustBe 'Left
    }
  }

  "move a  ticket with current status Todo to InProgress" should {
    "return an valid event" in {
      TicketCommand.UpdateStatusInProgress.validate("1234", existingTicketTodo).toEither mustBe Right(TicketEvent.StatusChanged("board1", InProgress))
    }
  }

  "move a  ticket with current status Todo to Todo" should {
    "return an error message" in {
      TicketCommand.UpdateStatusTodo.validate("1234", existingTicketTodo).toEither mustBe Left("wrong status")
    }
  }

  "move a  ticket with current status InProgress to InProgress" should {
    "return an error message" in {
      TicketCommand.UpdateStatusInProgress.validate("1234", existingTicketInProgress).toEither mustBe Left("wrong status")
    }
  }

  "move a  ticket with current status Done to Done" should {
    "return an error message" in {
      TicketCommand.UpdateStatusDone.validate("1234", existingTicketDone).toEither mustBe Left("wrong status")
    }
  }


}
