package eventHandlers

import event.TicketEvent
import org.scalatestplus.play.PlaySpec
import states.Ticket
import states.Ticket.{InProgress, Todo}

class TicketEventHandlerSpec extends PlaySpec {

  "Handle created event" should {
    "set title, description and status" in {
      TicketEventHandler.handle(TicketEvent.Created("id", "boardId", "title", "description"))(None) mustBe Some(Ticket(Todo, "boardId", "title", "description"))
    }
  }

  "Handle change status event" should {
    "update the " in {
      TicketEventHandler.handle(TicketEvent.StatusChanged("boardId", InProgress))(Some(Ticket(Todo, "boardId", "title", "description"))) mustBe Some(Ticket(InProgress, "boardId", "title", "description"))
    }
  }
}
