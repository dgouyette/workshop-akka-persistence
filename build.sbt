name := """workshop-akka-persistence"""
organization := "com.technov"

version := "1.0-SNAPSHOT"

resolvers += Resolver.jcenterRepo

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.6"
val AkkaVersion = "2.5.17"
val macwireVersion = "2.3.1"
val monocleVersion = "1.5.0"
resolvers += Resolver.jcenterRepo // Adds Bintray to resolvers for akka-persistence-redis and rediscala

libraryDependencies += "com.github.pureconfig" %% "pureconfig" % "0.9.1"

libraryDependencies += "com.softwaremill.macwire" %% "macros" % macwireVersion
libraryDependencies += "com.softwaremill.macwire" %% "macrosakka" % macwireVersion
libraryDependencies += "com.softwaremill.macwire" %% "util" % macwireVersion

libraryDependencies += "ca.mrvisser" %% "sealerate" % "0.0.5"

libraryDependencies += "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % AkkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-slf4j" % AkkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % AkkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-persistence-typed" % AkkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-persistence-query" % AkkaVersion
libraryDependencies += "be.wegenenverkeer" %% "akka-persistence-pg" % "0.10.0"
libraryDependencies += "com.github.dnvriend" %% "akka-persistence-jdbc" % "3.4.0"
libraryDependencies += "com.github.dnvriend" %% "akka-persistence-inmemory" % "2.5.15.1" % Test
libraryDependencies += "com.typesafe.akka" %% "akka-cluster-sharding-typed" % AkkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-cluster" % AkkaVersion


libraryDependencies += "com.typesafe.akka" %% "akka-actor-testkit-typed" % AkkaVersion % Test
libraryDependencies += "org.mockito" % "mockito-core" % "1.9.5" % Test


libraryDependencies += "io.github.jto" %% "validation-core" % "2.1.0"


libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test

libraryDependencies += "io.kanaka" %% "play-monadic-actions" % "2.1.0"
libraryDependencies += "io.kanaka" %% "play-monadic-actions-cats" % "2.1.0"


libraryDependencies += "io.github.scala-hamsters" %% "hamsters" % "3.0.0"

libraryDependencies += "org.julienrf" %% "play-json-derived-codecs" % "4.0.0"

libraryDependencies ++= Seq(
  "com.github.julien-truffaut" %% "monocle-core" % monocleVersion,
  "com.github.julien-truffaut" %% "monocle-macro" % monocleVersion)

libraryDependencies ++= Seq(
  jdbc,
  "org.playframework.anorm" %% "anorm" % "2.6.2",
  "org.playframework.anorm" %% "anorm-postgres" % "2.6.2"
)
libraryDependencies ++= Seq(
  "org.webjars" %% "webjars-play" % "2.6.3", //3.3.1
  "org.webjars.npm" % "vue" % "2.5.17",
  "org.webjars.npm" % "vue-router" % "3.0.1",
  "org.webjars.npm" % "vue-resource" % "1.2.0"
)

