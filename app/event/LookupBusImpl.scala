package event

import akka.actor.ActorRef
import akka.event.{EventBus, LookupClassification}
import play.api.Logger

//see https://doc.akka.io/docs/akka/2.5/event-bus.html
class LookupBusImpl  extends EventBus with LookupClassification {
  type Event = event.Event
  type Classifier = String
  type Subscriber = ActorRef

  override protected def classify(event: Event): Classifier = event.name

  override protected def publish(event: Event, subscriber: Subscriber): Unit = {
    Logger.info(s"$event published to $subscriber")
    subscriber ! event
  }

  override protected def compareSubscribers(a: Subscriber, b: Subscriber): Int =
    a.compareTo(b)

  override protected def mapSize: Int = 128

}