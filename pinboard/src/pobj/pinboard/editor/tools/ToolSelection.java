package pobj.pinboard.editor.tools;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import pobj.pinboard.document.Clip;
import pobj.pinboard.editor.CommandStack;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.editor.commands.Command;
import pobj.pinboard.editor.commands.CommandMove;

public class ToolSelection implements Tool {
    private double pressed_x;
    private double pressed_y;
    private List<Command> list=new ArrayList<>();
	@Override
	public void press(EditorInterface i, MouseEvent e) {
		// TODO Auto-generated method stub
		pressed_x=e.getX();
		pressed_y=e.getY();
		if(e.isShiftDown())
			i.getSelection().toogleSelect(i.getBoard(),e.getX(),e.getY());
		else
			i.getSelection().select(i.getBoard(),e.getX(),e.getY());
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		// TODO Auto-generated method stub
		for(Clip elem :i.getSelection().getContents()) {
			CommandMove mv=new CommandMove( i,elem,e.getX()-pressed_x, e.getY()-pressed_y);
			list.add(mv);
			mv.execute();
		}
		pressed_x = e.getX();
		pressed_y = e.getY();
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		// TODO Auto-generated method stub
		i.getBoard().draw(gc);
		i.getSelection().drawFeedback(gc);
	}

	@Override
	public String getName(EditorInterface editor) {
		// TODO Auto-generated method stub
		return "Selection";
	}
	public void addCommand(CommandStack stack) {
		for(Command cmd :list) {
			stack.addCommand(cmd);
		}
	}
}
