package pobj.pinboard.editor.commands;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.document.Clip;
import pobj.pinboard.editor.EditorInterface;

public class CommandRemove implements Command {
	private EditorInterface edit;
	private List<Clip> list =new ArrayList<>();
	
	public CommandRemove(EditorInterface editor, Clip toAdd) {
		this.edit=editor;
		this.list.add(toAdd);
		
	}
	public CommandRemove(EditorInterface editor, List<Clip> toAdd) {
		this.edit=editor;
		this.list.addAll(toAdd);
	}
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		edit.getBoard().removeClip(list);
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		edit.getBoard().addClip(list);
	}

}
