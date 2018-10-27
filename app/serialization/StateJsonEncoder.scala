package serialization

import akka.persistence.pg.JsonString
import play.api.libs.json.{JsObject, Json}
import states.{Board, Ticket}

class StateJsonEncoder extends akka.persistence.pg.event.JsonEncoder {

  override def toJson: PartialFunction[Any, JsonString] = ???

  override def fromJson: PartialFunction[(JsonString, Class[_]), AnyRef] = ???
}
