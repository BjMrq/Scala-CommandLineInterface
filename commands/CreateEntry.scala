package com.rtjvm.scala.oop.commands

import com.rtjvm.scala.oop.files.{DirEntry, Directory}
import com.rtjvm.scala.oop.filesystem.State

abstract class CreateEntry(name: String) extends Command{
  override def apply(state: State): State = {
    val wd = state.wd
    if (wd.hasEntry(name)){
      state.setMessage("Entry " + name + " already exist")
    } else if (checkIllegal(name)){
      state.setMessage(name + " illegal entry name")
    } else {
      doCreateEntry(state, name)
    }
  }

  def checkIllegal(name: String): Boolean = {
    name.contains(".") || name.contains(Directory.SEPARATOR)
  }

  def doCreateEntry(state: State, name: String): State = {
    def updateStructure(currentDirectory: Directory, path: List[String], newEntry: DirEntry): Directory = {
      if (path.isEmpty) currentDirectory.addEntry(newEntry)
      else {
        val oldEntry = currentDirectory.findEntry(path.head).asDirectory
        currentDirectory.replaceEntry(oldEntry.name, updateStructure(oldEntry, path.tail, newEntry))
      }
    }

    val wd = state.wd

    // 1. All directories in the full path
    val allDirsIndPath = wd.getAllFoldersInPath

    // 2. Create new directory entry in the wd
    val newEntry: DirEntry = createSpecificEntry(state)

    // 3. Update the whole directory structure from the root (has to stay IMMUTABLE)
    val newRoot = updateStructure(state.root, allDirsIndPath, newEntry)

    // 4. Find new working directory INSTANCE given wd full path in the NEW directory structure
    val newWd = newRoot.findDescendant(allDirsIndPath)

    State(newRoot, newWd)
  }
  def createSpecificEntry(state: State): DirEntry
}