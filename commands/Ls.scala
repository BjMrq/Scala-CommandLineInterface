package com.rtjvm.scala.oop.commands

import com.rtjvm.scala.oop.files.DirEntry
import com.rtjvm.scala.oop.filesystem.State

class Ls extends Command{
  override def apply(state: State): State = {
    val content = state.wd.content
    val listOutput = createListOutput(content)
    state.setMessage(listOutput)
  }

  def createListOutput(content: List[DirEntry]): String = {
    if (content.isEmpty) ""
    else {
      val entry = content.head
      entry.name + " - [" + entry.getType + "]\n" + createListOutput(content.tail)
    }


  }
}
