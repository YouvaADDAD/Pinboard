package pobj.pinboard.editor;

import java.util.ArrayDeque;
import java.util.Deque;

import pobj.pinboard.editor.commands.Command;

public class CommandStack {
   private Deque<Command> undo = new ArrayDeque<>();
   private Deque<Command> redo = new ArrayDeque<>();
   
   public void addCommand(Command cmd) {
	   undo.push(cmd);
	   redo.clear();
   }
   public void undo() {
	   Command tmp=undo.pop();
	   tmp.undo();
	   redo.push(tmp);
   }
   public void redo() {
	   Command tmp=redo.pop();
	   tmp.execute();
	   undo.push(tmp);
   }
   public boolean isUndoEmpty() {
	   return undo.isEmpty();
   }
   public boolean isRedoEmpty() {
	   return redo.isEmpty();
   }
}
