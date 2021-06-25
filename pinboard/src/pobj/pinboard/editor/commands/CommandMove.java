package pobj.pinboard.editor.commands;


import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.document.Clip;
import pobj.pinboard.editor.EditorInterface;


public class CommandMove implements Command {
    private double x;
    private double y;
    private List<Clip> list=new ArrayList<>();
    
    public CommandMove(EditorInterface editor, Clip toMove, double x, double y) {
		this.x = x;
		this.y = y;
		list.add(toMove);
	}

	public CommandMove(EditorInterface editor, List<Clip> toMove, double x, double y) {
		
		this.x = x;
		this.y = y;
		list.addAll(toMove);
	}
    	
    
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		for(Clip e :list) {
		    e.move(x, y);
		}
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		for(Clip e :list) {
		    e.move(-x, -y);
		}
	}

}
