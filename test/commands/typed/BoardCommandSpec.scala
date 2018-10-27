package commands.typed

import akka.actor.testkit.typed.scaladsl.{ScalaTestWithActorTestKit, TestProbe}
import cats.data.Validated
import event.BoardEvent
import org.scalatest.WordSpecLike
import utils.TestConfig

class BoardCommandSpec extends ScalaTestWithActorTestKit(TestConfig.config)
  with WordSpecLike {

  val probe = TestProbe[Either[Command.Error, BoardEvent]]()


  "Create a board with a valid title and description" must {
    "return an  event" in {
      BoardCommand.Create("title", "description", probe.ref).validate("1234", None) shouldBe Validated.valid(BoardEvent.Created("1234", "title", "description"))
    }
  }

  "Create a board with a invalid title and description" must {
    "return an  event" in {
      BoardCommand.Create("", "description", probe.ref).validate("1234", None) shouldBe Validated.invalid("description or title must not be empty")
    }
  }

}
