### Workshop version typée



#### PersistentBehaviors

```
val behavior: Behavior[Command] =
  PersistentBehaviors.receive[Command, Event, State](
    persistenceId = "abc",
    emptyState = State(),
    commandHandler = ???,
    eventHandler = ??? 
  )
```
**emptyState** = état initial

Le premier type est **Command**, il indiquera quel type de message (**Protocole**) l'acteur est capable de recevoir.

**CommandHandler**
Celui ci définit comment traiter une commande en produisant des effects  :
 * persistence d'event
 * stopper l'acteur persistant...
 
 `(State, Command) => Effect[Event, State]`

**EventHandler**

Retourne un nouvel état après l'application d'un évènement

`(State, Event) => State)`

## Traiter et valider une commande

Nous allons créer la première commande de création de dashboard.
Celle ci devra hériter du trait  dont la signature est suivante. 

```
trait Command[E <: Event, S <: State] {
     // acteur à qui la réponse sera envoyée
     val replyTo :  ActorRef[Either[Command.Error, E]]
     
     // une commande peut être valide ou invalide selon l'état de la persistance
     def validate(id : String, persisted: Option[S]): Either[Command.Error, E]
}
```

## Créer un Acteur persistant typé


## Tester un acteur persistant

## Envoyer et communiquer la réponse d’une commande à l’utilisateur

## Enregistrer un évènement 

## Enregistrer l’état

## Gérer le comportement de sortie

## Ecouter des événements pour faire réagir le système (websocket)

## Clustering

## Sharding

## Monitoring