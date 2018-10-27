package serialization

import akka.persistence.pg.JsonString
import org.scalatestplus.play.PlaySpec
import states.{Board, Ticket}
import states.Board.Running
import states.Ticket.Todo

class StateJsonEncoderSpec extends PlaySpec {

  val stateJsonEncoder = new StateJsonEncoder()
  val expectedBoardJson = """{"title":"title","description":"description","status":{"type":"Running"},"type":"Board"}"""
  val expectedBoard = Board("title", "description", Running)
  val expectedTicketAsJson = """{"status":{"type":"Todo"},"boardId":"boardId","title":"title","description":"description","type":"Ticket"}"""
  val expectedTicket = Ticket(Todo, "boardId", "title", "description")

  "board state " should {
    "serializable to json" in {
      stateJsonEncoder.toJson(expectedBoard).value mustBe expectedBoardJson
    }
  }

  "json board state " should {
    "readable from json" in {
      stateJsonEncoder.fromJson((JsonString(expectedBoardJson), Board.getClass)) mustBe Some(expectedBoard)
    }
  }

  "ticket state " should {
    "serializable to json" in {
      stateJsonEncoder.toJson(expectedTicket).value mustBe expectedTicketAsJson
    }
  }

  "None" should {
    "serializable to json" in {
      stateJsonEncoder.toJson(None).value mustBe """{"type" : "None"}"""
    }
  }
}