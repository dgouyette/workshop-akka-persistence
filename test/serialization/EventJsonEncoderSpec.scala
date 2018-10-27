package serialization

import akka.persistence.pg.JsonString
import event.{BoardEvent, TicketEvent}
import org.scalatestplus.play.PlaySpec

class EventJsonEncoderSpec extends PlaySpec {

  val jsonEncoder = new EventJsonEncoder()

  val boardEventCreated = BoardEvent.Created("id", "title", "description")
  val expectedBoardCreatedJson = """{"type":"Created","id":"id","title":"title","description":"description"}"""

  val expectedTicketCreatedEvent = TicketEvent.Created("id", "boardId", "title", "description")
  val expectedTicketEventCreatedJson = """{"type":"Created","description":"description","id":"id","status":{"type":"Todo"},"boardId":"boardId","title":"title"}"""

  "board event " should {
    "serializable to json" in {
      jsonEncoder.toJson(boardEventCreated).value mustBe expectedBoardCreatedJson
    }
  }

  "json event" should {
    "be readable to event" in {
      jsonEncoder.fromJson((JsonString(expectedBoardCreatedJson), BoardEvent.getClass)) mustBe boardEventCreated
    }
  }

  "ticket event " should {
    "serializable to json" in {
      jsonEncoder.toJson(expectedTicketCreatedEvent).value mustBe expectedTicketEventCreatedJson
    }
  }
}
