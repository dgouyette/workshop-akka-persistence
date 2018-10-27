package persistentActors.typed

import akka.actor.ActorSystem
import akka.actor.testkit.typed.scaladsl.{ScalaTestWithActorTestKit, TestProbe}
import akka.actor.typed.ActorRef
import akka.actor.typed.scaladsl.AskPattern._
import commands.typed.{Command, TicketCommand}
import event.TicketEvent
import event.TicketEvent.Created
import org.scalatest.WordSpecLike
import org.scalatest.mockito.MockitoSugar
import play.api.db.Database
import utils.TestConfig

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

class TicketPersistenceActorSpec extends ScalaTestWithActorTestKit(TestConfig.config)
  with WordSpecLike with MockitoSugar {

  val probe = TestProbe[Either[Command.Error, TicketEvent]]()


  implicit val datatabase = mock[Database]
  implicit val as = mock[ActorSystem]

  def testedActorRef(persistenceId: String): ActorRef[TicketCommand] = spawn(TicketPersistenceActor.behavior(persistenceId))

  "Create a ticket with a valid command" must {
    "return a  right Created event" in {
      val res = Await.result(testedActorRef("ticket1") ? ((ref: ActorRef[Either[String, TicketEvent]]) => TicketCommand.Create("board1", "title", "description", ref)), 3 seconds)
      res shouldBe Right(Created("ticket1", "board1", "title", "description"))
    }
  }
}