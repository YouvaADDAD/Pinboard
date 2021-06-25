package pobj.pinboard.editor.commands;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.document.Clip;
import pobj.pinboard.document.ClipGroup;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.editor.commands.Command;

public class CommandGroup implements Command {
	private EditorInterface edit;
	private List<Clip> list =new ArrayList<>();
	private ClipGroup tmp;
	public CommandGroup(EditorInterface editor, List<Clip> rects) {
		// TODO Auto-generated constructor stub
		this.edit=editor;
		this.list.addAll(rects);
		
	}
	public CommandGroup(EditorInterface editor, Clip rects) {
		// TODO Auto-generated constructor stub
		this.edit=editor;
		this.list.add(rects);
		
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		tmp=new ClipGroup();
		edit.getBoard().removeClip(list);
		for(Clip e : list) {
			tmp.addClip(e);
		}
		edit.getBoard().addClip(tmp);
		
		
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		edit.getBoard().removeClip(tmp);
		edit.getBoard().addClip(list);
		
	}
   
}
