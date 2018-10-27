package persistentActors.untyped

import akka.actor.ActorSystem
import akka.persistence.{PersistentActor, SaveSnapshotSuccess, SnapshotOffer, SnapshotSelectionCriteria}
import commands.untyped.Command
import event.{Event, LookupBusImpl}
import eventHandlers.EventHandler
import states.State


trait DefaultPersistentActor[S <: State, E <: Event, C <: Command[E, S]] extends PersistentActor {
  var state: Option[S] = None

  implicit def as: ActorSystem

  val eventHandler: EventHandler[S, E]

  implicit val eventBus: LookupBusImpl

  override def receiveRecover: Receive = {
    case SnapshotOffer(_, stateOpt: Option[S]) =>
      state = stateOpt
      saveSnapshot(state)
  }

  override def receiveCommand: Receive = {
    case c: C => {
      //ici on valide la commande c
      //ici on applique l'event sur le stae
      //ici on sauvegarde le snapshot
      //ici  on publie le resultat dans l'eventbut
      ???
    }
  }
}
