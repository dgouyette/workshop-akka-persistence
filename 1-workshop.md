## Introduction

L'**event sourcing** est un pattern d'architecture qui se focalise sur l'application d'une succession de changements (events)  à un état (Snapshot).
Par opposition à l'approche tranditionnelle où chaque enregistrement va écraser l'état précédent sans possibilité de revenir en arrière,
il permettra au travers des events de connaître  la succession d'action qui ont amené l'élément à son état courant.

 
Parler d'évènement est quelque chose de très naturel. Nous allons nous baser dans ce workshop sur un trello très simple dans ses fonctionnalités.

Nous allons créer un dashboard, auquel nous pourrons ensuite ajouter des tickets et faire changer leur statut. 

Un dashboard  = titre + description  + statut (Running ou Archived)
Un ticket = titre + description  + statut (Todo, InProgress, Done)

Un event sera par convention le nom d'une action appliquée à une entité le tout au passé (exemple BoardStatusUpdated, BoardTitleUpdated)

L'évènement de création sera par exemple BoardCreated avec deux propriétés **title + description**
Pour ensuite modifier le texte il faudra créér un nouvel évènement TitleUpdated

La clé d'identification unique d'une entité sera appelée **persistenceId**

Les events seront stockés dans une table journal tandis que les états courants seront stockés dans la table snapshot.
Cette structure sera créé automatiquement lors de la première exécution de la commande `docker-compose up`

La structure des tables sera : 

**Journal**

```
     Column      |          Type          |
-----------------+------------------------+
 ordering        | bigint                 |
 persistence_id  | character varying(255) |
 sequence_number | bigint                 |
 deleted         | boolean                |
 tags            | character varying(255) |
 message         | bytea                  |
```

**Snapshot** 

```
    Column     |          Type          
---------------+------------------------
 persistenceid | character varying(254) 
 sequencenr    | integer                
 timestamp     | bigint                 
 snapshot      | bytea                  
 manifest      | character varying(512) 
 json          | json                   
```

A noter que deux plugins différents ont été utilisé (snapshot et journal) car il y a un bug sur akka-persistence-pg avec une version d'akka > 2.5. 
Cela illustre que vous pouvez utiliser deux basess de persistence différentes ;)

Nous allons également utiliser CQRS (Command Query Responsibility Segregation) qui repose sur un principe simple : 
séparer au sein d'une application la notion de traitement métier de la restitution de l'information.

La partie écriture sera traitée via l'exécution d'une commande.
Si la commande est valide elle sera transformée en event. L'event servira à modifier l'état de l'objet puis sera retourné à l'appelant pour lui signifier qu'il a été appliqué.
Si la commande est invalide, une erreur sera envoyée à l'appelant pour lui signifier les raisons du refus : champ manquant, statut invalide...
Une commande sera créée sous la forme d'un verbe + l'entité qui s'y rattache : CreateBoard, CreateTicket, UpdateStatusTicket...

Le workshop est proposé en deux versions : version akka typed ou non.
Certaines parties sont communes, d'autres pas.  

Des tests unitaires ont déjà été écrits. 
Ils seront à faire passer au vert pour vous guider au travers des différentes étapes.
Un certain nombre de classe existe déjà pour vous simplifier également le travail.

L'ordre préféré des tests est : 

* commands
* eventHandlers
* persitentActors
* serialization
* controllers

Côté front, pour que celui-ci utilise l'api untyped, il faudra aller le modifier dans DashboardShow.js  : 

`const prefix = '/api/v1/typed';` à remplacer par `const prefix = '/api/v1/untyped';`

Bon courage
