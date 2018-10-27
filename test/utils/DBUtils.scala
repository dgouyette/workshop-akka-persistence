package utils

import play.api.db.Databases
import anorm.{SQL, SqlParser}

object DBUtils {
  val persistenceId = SqlParser.str("persistenceId")

  val database = Databases(
    driver = "org.postgresql.Driver",
    url = "jdbc:postgresql://localhost:5432/workshop?user=docker&password=docker"
  )

  def truncate = {
    database.withConnection {
      implicit c =>
        SQL("truncate journal, snapshot;").execute()
    }
  }

  def findBoardId = database.withConnection {
    implicit c =>
      SQL("select persistenceid from snapshot where (json ->> 'type') = 'Board';").as(persistenceId.*).head
  }

  def findTicketId = database.withConnection {
    implicit c =>
      SQL("select persistenceid from snapshot where (json ->> 'type') = 'Ticket';").as(persistenceId.*).head
  }


}
