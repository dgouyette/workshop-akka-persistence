package controllers.untyped

import org.scalatest.BeforeAndAfterAll
import org.scalatestplus.play.{BaseOneAppPerTest, PlaySpec}
import play.api.test.Helpers._
import play.api.test._
import utils.{DBUtils, FakeApplicationBuilder}

class TicketControllerSpec extends PlaySpec with BaseOneAppPerTest with FakeApplicationBuilder   with BeforeAndAfterAll {

  override protected def beforeAll(): Unit = DBUtils.truncate

  "create a ticket with valid informations" should {
    "return Created" in {
      val request = FakeRequest(POST, routes.TicketController.create("board1").url)
        .withHeaders(CONTENT_TYPE -> "application/json").withBody(
        """
          |{"title":"ticket n°1","description":"this is a description"}
        """.stripMargin)

      val reponse = route(app, request).get
      status(reponse) mustBe CREATED

    }
  }

 /** "create a ticket with invalid informations" should {
    "return Created" in {
        val request = FakeRequest(POST, routes.TicketController.create("board1").url)
          .withHeaders(CONTENT_TYPE -> "application/json").withBody(
          """
            |{"title":"ticket n°1"}
          """.stripMargin)

      val reponse = route(app, request).get
      status(reponse) mustBe BAD_REQUEST

    }
  }**/


  "update status of missing ticket" should {
    "return not found" in {
      val request = FakeRequest(PUT, routes.TicketController.updateStatusInProgress("board1", "ticket12345").url)
        .withHeaders(CONTENT_TYPE -> "application/json")

      val reponse = route(app, request).get
      status(reponse) mustBe BAD_REQUEST

    }
  }

  "update status of existing ticket to in progress" should {
    "answer ok" in {
      val id = DBUtils.findTicketId
      val request = FakeRequest(PUT, routes.TicketController.updateStatusInProgress("board1", id).url)
        .withHeaders(CONTENT_TYPE -> "application/json")

      val reponse = route(app, request).get
      status(reponse) mustBe OK

    }
  }
  "update status of existing ticket to done" should {
    "answer ok" in {
      val request = FakeRequest(PUT, routes.TicketController.updateStatusDone("board1", DBUtils.findTicketId).url)
        .withHeaders(CONTENT_TYPE -> "application/json")

      val reponse = route(app, request).get
      status(reponse) mustBe OK

    }
  }

  "update status of existing ticket twice" should {
    "answer ok" in {



      val request1 = FakeRequest(PUT, routes.TicketController.updateStatusInProgress("board1", DBUtils.findTicketId).url)
        .withHeaders(CONTENT_TYPE -> "application/json")

      val reponse1 = route(app, request1).get
      status(reponse1) mustBe OK

      val request2 = FakeRequest(PUT, routes.TicketController.updateStatusTodo("board1", DBUtils.findTicketId).url)
        .withHeaders(CONTENT_TYPE -> "application/json")

      val reponse2 = route(app, request2).get
      status(reponse2) mustBe OK


    }
  }



}
