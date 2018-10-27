package commands.untyped

import event.BoardEvent
import states.Board

sealed trait BoardCommand extends Command[BoardEvent, Board]

object BoardCommand {
  case class Create(title: String, description: String) extends BoardCommand
  case object Archive extends BoardCommand

}