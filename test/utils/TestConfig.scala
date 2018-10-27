package utils

import com.typesafe.config.{Config, ConfigFactory}

object TestConfig {
  val config: Config = ConfigFactory.parseString(
    """
      |akka {
      |  persistence {
      |    journal.plugin = "inmemory-journal"
      |    snapshot-store.plugin = "inmemory-snapshot-store"
      |  }
      |}
    """.stripMargin)
}
