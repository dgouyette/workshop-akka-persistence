package persistentActors.typed

import akka.actor.ActorSystem
import akka.actor.testkit.typed.scaladsl.{ScalaTestWithActorTestKit, TestProbe}
import akka.actor.typed.ActorRef
import akka.actor.typed.scaladsl.AskPattern._
import commands.typed.{BoardCommand, Command}
import event.BoardEvent
import event.BoardEvent.Created
import org.scalatest.WordSpecLike
import org.scalatest.mockito.MockitoSugar
import play.api.db.Database
import utils.TestConfig

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

class BoardPersistenceActorSpec extends ScalaTestWithActorTestKit(TestConfig.config)
  with WordSpecLike with MockitoSugar  {

  val probe = TestProbe[Either[Command.Error, BoardEvent]]()

  implicit val datatabase = mock[Database]
  implicit val as = mock[ActorSystem]

  def testedActorRef(persistenceId: String): ActorRef[BoardCommand] = spawn(BoardPersistenceActor.behavior(persistenceId))

  "Create a board with a valid command" must {
    "return a  right Created event" in {
      val res = Await.result(testedActorRef("1234") ? ((ref: ActorRef[Either[String, BoardEvent]]) => BoardCommand.Create("title", "description", ref)), 3 seconds)
      res shouldBe Right(Created("1234", "title", "description"))
    }
  }

  "Create a board with an invalid command (empty title)" must {
    "return a  left Message" in {
      val res = Await.result(testedActorRef("5678") ? ((ref: ActorRef[Either[String, BoardEvent]]) => BoardCommand.Create("", "description", ref)), 3 seconds)
      res shouldBe Left("description or title must not be empty")
    }
  }

  "Try to create  a board with an existing persistenceid " must {
    "return a left message" in {
      val res = Await.result(testedActorRef("1234") ? ((ref: ActorRef[Either[String, BoardEvent]]) => BoardCommand.Create("title", "description", ref)), 3 seconds)
      res shouldBe Left("board already exists")
    }
  }
}
