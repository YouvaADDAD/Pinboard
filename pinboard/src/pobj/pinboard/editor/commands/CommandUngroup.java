package pobj.pinboard.editor.commands;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.document.Clip;
import pobj.pinboard.document.ClipGroup;
import pobj.pinboard.editor.EditorInterface;

public class CommandUngroup implements Command {
	private EditorInterface edit;
	private List<Clip> list =new ArrayList<>();
	public CommandUngroup(EditorInterface editor, List<Clip> rects) {
		// TODO Auto-generated constructor stub
		this.edit=editor;
		this.list=rects;
		
	}
	public CommandUngroup(EditorInterface editor, Clip rects) {
		// TODO Auto-generated constructor stub
		this.edit=editor;
		this.list.add(rects);
		
		
	}
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		for(Clip elem: list) {
			if(elem instanceof ClipGroup) {
				edit.getBoard().removeClip(elem);
				for(Clip elem2 : ((ClipGroup) elem).getClips()) {
					edit.getBoard().addClip(elem2);
				}
			}
		}
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		for(Clip elem: list) {
			if(elem instanceof ClipGroup) {
				for(Clip elem2 : ((ClipGroup) elem).getClips()) {
					edit.getBoard().removeClip(elem2);
				}
			}
		}
		edit.getBoard().addClip(list);
	}

}
