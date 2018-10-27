package eventHandlers

import event.BoardEvent
import org.scalatestplus.play.PlaySpec
import states.Board
import states.Board.{Archived, Running}

class BoardEventHandlerSpec extends PlaySpec {

  "Handle created event" should {
    "set title, description and status" in {
      BoardEventHandler.handle(BoardEvent.Created("id", "title", "description"))(None) mustBe Some(Board("title", "description", Running))
    }
  }

  "Handle Archived event" should {
    "set title, description and status" in {
      BoardEventHandler.handle(BoardEvent.StatusChanged(Archived))(Some(Board("title", "description", Running))) mustBe Some(Board("title", "description", Archived))
    }
  }

}
