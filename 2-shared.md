### Websocket & events

La partie front appelle des routes se terminant par events. C'est une route spécifique WebSocket.
Sur celle-ci seront émis tous les messages concernant une ou plusieurs entités.

Pour exposer une route websocket avec play, vous pouvez vous référer à cette page  : https://www.playframework.com/documentation/2.6.x/ScalaWebSockets

```
def socket = WebSocket.accept[String, String] { request =>

  // Log events to the console
  val in = Sink.foreach[String](println)

  // Send a single 'Hello!' message and then leave the socket open
  val out = Source.single("Hello!").concat(Source.maybe)

  Flow.fromSinkAndSource(in, out)
}
  

```

```
Pour récupérer via akka stream les events il faudra passer par le persistence journal : 
  val readJournal = PersistenceQuery(actorSystem).readJournalFor[JdbcReadJournal](JdbcReadJournal.Identifier)
  val eventSource = readJournal.eventXXX...
  Flow.fromSinkAndSource(Sink.ignore, eventSource)
```

### Générer des id de persistence

Pour générer les identifiants de persitance, un bon moyen est d'utiliser **UUID**
`val newId = UUID.randomUUID().toString`

