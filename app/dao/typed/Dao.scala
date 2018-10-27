package dao.typed

import anorm.{SqlParser, postgresql}
import play.api.libs.json.JsObject

object Dao {
  import postgresql._
  val persistenceIdAndJson = SqlParser.str("persistenceId") ~ SqlParser.get[JsObject]("json")
}
