package commands.typed

import akka.actor.testkit.typed.scaladsl.{ScalaTestWithActorTestKit, TestProbe}
import cats.data.Validated
import event.TicketEvent
import org.scalatest.WordSpecLike
import states.Ticket
import states.Ticket.{Done, InProgress, Todo}
import utils.TestConfig

class TicketCommandSpec extends ScalaTestWithActorTestKit(TestConfig.config)
  with WordSpecLike {

  val probe = TestProbe[Either[Command.Error, TicketEvent]]()
  val existingTicketTodo = Some(Ticket(Todo, "board1", "title", "description"))
  val existingTicketInProgress = Some(Ticket(InProgress, "board1", "title", "description"))
  val existingTicketDone = Some(Ticket(Done, "board1", "title", "description"))

  "Create a ticket with a valid title and description" should {
    "return an  event" in {
      TicketCommand.Create("1234", "title", "description", probe.ref).validate("1234", None).toEither shouldBe 'Right
    }
  }

  "Create a ticket with an empty title or description" should {
    "return an error message" in {
      TicketCommand.Create("1234", "", "description", probe.ref).validate("1234", None).toEither shouldBe 'Left
    }
  }

  "move a  ticket with current status Todo to InProgress" should {
    "return an valid event" in {
      TicketCommand.UpdateStatusInProgress(probe.ref).validate("1234", existingTicketTodo) shouldBe Validated.valid(TicketEvent.StatusChanged("board1", InProgress))
    }
  }

  "move a  ticket with current status Todo to Todo" should {
    "return an error message" in {
      TicketCommand.UpdateStatusTodo(probe.ref).validate("1234", existingTicketTodo) shouldBe Validated.invalid("wrong status")
    }
  }

  "move a  ticket with current status InProgress to InProgress" should {
    "return an error message" in {
      TicketCommand.UpdateStatusInProgress(probe.ref).validate("1234", existingTicketInProgress) shouldBe Validated.invalid("wrong status")
    }
  }

  "move a  ticket with current status Done to Done" should {
    "return an error message" in {
      TicketCommand.UpdateStatusDone(probe.ref).validate("1234", existingTicketDone) shouldBe Validated.invalid("wrong status")
    }
  }


}
