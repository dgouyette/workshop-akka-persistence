package persistentActors.typed

import akka.actor.ActorSystem
import akka.actor.typed.Behavior
import akka.persistence.typed.scaladsl.PersistentBehaviors
import commands.typed.TicketCommand
import event.TicketEvent
import play.api.db.Database

object TicketPersistenceActor {

  def behavior(persistenceId: String)(implicit database : Database, actorSystem : ActorSystem): Behavior[TicketCommand] = PersistentBehaviors
    .receive[TicketCommand, TicketEvent, Option[states.Ticket]](
    persistenceId, None,
    ??? ,//ici on valide les commande et retourne le résultat à l'envoyeur
    ??? // ici on utilise l'event handler pour modifier l'état courant
  )
}
//ici on sauvegarde le snapshot