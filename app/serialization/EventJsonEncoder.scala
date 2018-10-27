package serialization

import akka.persistence.pg.JsonString
import event.{BoardEvent, TicketEvent}
import play.api.libs.json.{JsObject, Json}

class EventJsonEncoder extends akka.persistence.pg.event.JsonEncoder {

  override def toJson: PartialFunction[Any, JsonString] = ???

  override def fromJson: PartialFunction[(JsonString, Class[_]), AnyRef] = ???
}
