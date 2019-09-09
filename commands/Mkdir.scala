package com.rtjvm.scala.oop.commands

import com.rtjvm.scala.oop.files.Directory
import com.rtjvm.scala.oop.filesystem.State

class Mkdir(name: String) extends Command {
  override def apply(state: State): State = {
    val wd = state.wd
    if (wd.hasEntry(name)){
      state.setMessage("Entry " + name + " already exist")
    } else if (checkIllegal(name)){
      state.setMessage(name + " illegal entry name")
    } else {
      doMkdir(state, name)
    }
  }

  def checkIllegal(name: String): Boolean = {
    name.contains(".") || name.contains(Directory.SEPARATOR)
  }

  def doMkdir(state: State, name: String): State = {
    
  }
}
