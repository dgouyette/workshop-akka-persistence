package play.api.libs.parsers

import akka.stream.Materializer
import play.api.http.ParserConfiguration
import play.api.libs.Files.TemporaryFileCreator
import play.api.libs.json.{JsError, Reads}
import play.api.mvc.{BodyParser, PlayBodyParsers, Results}

import scala.concurrent.Future

trait DefaultBP extends PlayBodyParsers {

  implicit def materializer: Materializer
  def config: ParserConfiguration
  def temporaryFileCreator: TemporaryFileCreator
  implicit val errorHandler = this.errorHandler

  override def json[A](implicit reader: Reads[A]) = {
    BodyParser("json reader") { request =>
      import play.core.Execution.Implicits.trampoline
      json(request) mapFuture {
        case Left(simpleResult) =>
          Future.successful(Left(simpleResult))
        case Right(jsValue) =>
          jsValue.validate(reader) map { a =>
            Future.successful(Right(a))
          } recoverTotal { jsError =>
            Future.successful(Left(Results.BadRequest(JsError.toJson(jsError))))
          }
      }
    }
  }
}

class DefaultBPUBIBodyParsers(val config: ParserConfiguration,
                              val materializer: Materializer,
                              val temporaryFileCreator: TemporaryFileCreator) extends DefaultBP
