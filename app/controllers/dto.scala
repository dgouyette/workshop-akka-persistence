package controllers {

  import play.api.libs.json.{Json, Reads}

  case class CreateBoardRequest(title: String, description: String)

  object CreateBoardRequest {
    implicit val r: Reads[CreateBoardRequest] = Json.reads[CreateBoardRequest]
  }

  case class CreateTicketRequest(title: String, description: String)

  object CreateTicketRequest {
    implicit val r = Json.reads[CreateTicketRequest]
  }
}
