package persistentActors.typed

import akka.actor.ActorSystem
import akka.actor.typed.Behavior
import akka.persistence.typed.scaladsl.PersistentBehaviors
import commands.typed.BoardCommand
import event.BoardEvent
import play.api.db.Database

object BoardPersistenceActor {

  def behavior(persistenceId: String)(implicit database : Database, actorSystem : ActorSystem): Behavior[BoardCommand] = PersistentBehaviors
    .receive[BoardCommand, BoardEvent, Option[states.Board]](
    persistenceId, None,
    ??? ,//ici on valide les commande et retourne le résultat à l'envoyeur
    ??? // ici on utilise l'event handler pour modifier l'état courant
  )
}
//ici on sauvegarde le snapshot