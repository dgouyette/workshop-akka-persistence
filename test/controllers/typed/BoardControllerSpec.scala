package controllers.typed

import org.scalatest.BeforeAndAfterAll
import org.scalatestplus.play.{BaseOneAppPerTest, PlaySpec}
import play.api.test.Helpers._
import play.api.test._
import utils.{DBUtils, FakeApplicationBuilder}



class BoardControllerSpec extends PlaySpec with BaseOneAppPerTest with FakeApplicationBuilder   with BeforeAndAfterAll {

  override protected def beforeAll(): Unit = DBUtils.truncate

  "create a board with valid informations" should {
    "return Created" in {
      val request = FakeRequest(POST, routes.BoardController.create().url)
        .withHeaders(CONTENT_TYPE -> "application/json").withBody(
          """
            |{"title":"board n°1","description":"this is a description"}
          """.stripMargin)
      val reponse = route(app, request).get
      status(reponse) mustBe CREATED
    }
  }

  "create a board with missing informations" should {
    "return Bad request" in {
      val request = FakeRequest(POST, routes.BoardController.create().url)
        .withHeaders(CONTENT_TYPE -> "application/json").withBody(
        """
          |{"title":"board n°1"}
        """.stripMargin)
      val reponse = route(app, request).get
      status(reponse) mustBe BAD_REQUEST
    }
  }
}