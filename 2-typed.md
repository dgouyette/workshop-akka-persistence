### Workshop version typée

### PersistentBehaviors

Le persitent behavior est chargé de prendre une commande en entrée.
C'est le rôle du bahavior de s'assurer de la persistence des Events et des snapshots

Effect.persist(event) permettra de sauvegarder l'event
andThen permettra de chainer les effets suivants


Plus de détails ici : https://doc.akka.io/docs/akka/2.5.17/typed/persistence.html


### ASK
Ask version typée : 

Pour envoyer un message à un acteur tout en attendant une réponse, Le sender  doit être ajouté au message d'envoi. 

Vous devrez procéder de la façon suivante : 

```
import akka.actor.typed.scaladsl.AskPattern._
persitentActor(persistenceId) ?   ((ref: ActorRef[Either[Command.Error, BoardEvent]]) => Command.DoAction(value, ref))
``` 

En fonction du type du retour, il faudra afficher des statut http. 
Par exemple : 
*  Right(BoardEvent.Created(_)) :   201 quand l'entité a été créée
*  Left("invalid status") :  400 quand la requête a été refusée


### Récupérer une instance d'un acteur : 

Il n'est pas recommadé de créer dans une même application plusieurs ActorSytem
L'import de l'adapter permet de réutiliser celui venant de play, qui lui n'est pas typé.
La méthode spawn est l'équivalent de  `actorOf`
```
import akka.actor.typed.scaladsl.adapter._
actorSystem.spawnAnonymous(PersistenceActor.behavior(persistenceId))

```


### Sharding

Pour récupérer une référence vers l'acteur shardé il faut utiliser la syntaxe suivante : 


```
 val sharding = ClusterSharding(actorSystem.toTyped)
  def shardedboardPersistentActor(boardId: String): EntityRef[BoardCommand] = {
    val TypeKey = EntityTypeKey[BoardCommand]("boardCommand")

    sharding.start(ShardedEntity(
      create = (_, entityId) ⇒ BoardPersistenceActor.behavior(entityId),
      typeKey = TypeKey,
      stopMessage = Exit(actorSystem.deadLetters.toTyped)))
    sharding.entityRefFor(TypeKey, boardId)
  }
```