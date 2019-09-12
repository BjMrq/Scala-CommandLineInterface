package com.rtjvm.scala.oop.filesystem

import com.rtjvm.scala.oop.commands.Command
import com.rtjvm.scala.oop.files.Directory

object Filesystem extends App {

  val root = Directory.ROOT()

  val initialState = State(root, root)
  initialState.show()

  io.Source.stdin.getLines().foldLeft(initialState)((currentState, newLine) => {
    val newState = Command.from(newLine).apply(currentState) // returns the newState!
    newState.show()
    newState
  })
}
