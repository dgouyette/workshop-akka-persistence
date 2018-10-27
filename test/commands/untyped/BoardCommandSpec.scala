package commands.untyped


import cats.data.Validated
import event.BoardEvent
import org.scalatestplus.play.PlaySpec

class BoardCommandSpec extends PlaySpec  {

  "Create a board with a valid title and description" should {
    "return an  event" in {
      BoardCommand.Create("title", "description").validate("1234", None) mustBe  Validated.valid(BoardEvent.Created("1234", "title", "description"))
    }
  }

  "Create a board with a invalid title and description" must {
    "return an  event" in {
      BoardCommand.Create("", "description").validate("1234", None) mustBe Validated.invalid("description or title must not be empty")
    }
  }

}