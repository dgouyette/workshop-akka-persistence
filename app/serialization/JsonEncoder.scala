package serialization

import akka.persistence.pg.JsonString

trait JsonEncoder {

  /**
    * A partial function that serializes an event to a json representation
    * @return the json representation
    */
  def toJson: PartialFunction[Any, JsonString]

  /**
    * A partial function that deserializes an event from a json representation
    * @return the event
    */
  def fromJson: PartialFunction[(JsonString, Class[_]), AnyRef]

}
