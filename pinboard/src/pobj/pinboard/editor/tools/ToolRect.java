package pobj.pinboard.editor.tools;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.document.Clip;
import pobj.pinboard.editor.CommandStack;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.editor.commands.Command;
import pobj.pinboard.editor.commands.CommandAdd;
import pobj.pinboard.document.ClipRect;

public class ToolRect implements Tool {
   private Clip rect;
   private double press_x;
   private double press_y;
   private double release_x;
   private double release_y;
   private List<Command> list=new ArrayList<>();
   private Color color;
  
	@Override
	public void press(EditorInterface i, MouseEvent e) {
		press_x=e.getX();
		press_y=e.getY();
		rect = new ClipRect(press_x, press_y, press_x, press_y,this.color);
		
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		if(rect.getLeft() > e.getX() || rect.getTop() > e.getY()){
			rect.setGeometry(e.getX(),e.getY(), rect.getRight(),rect.getBottom());
		}else {
			rect.setGeometry(rect.getLeft(),rect.getTop(), e.getX(),e.getY()); 
		}
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		release_x=e.getX();
		release_y=e.getY();
		
		if(press_x > release_x || press_y > release_y){
			rect.setGeometry(release_x,release_y, press_x,press_y);
		}else {
			rect.setGeometry(press_x,press_y, release_x,release_y); 
		}
		CommandAdd cv=new CommandAdd(i,rect);
		list.add(cv);
		cv.execute();
		
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		gc.setStroke(this.color);
		gc.strokeRect(rect.getLeft(), rect.getTop(),rect.getRight()-rect.getLeft(),rect.getBottom()-rect.getTop());
		
	}

	@Override
	public String getName(EditorInterface editor) {
		// TODO Auto-generated method stub
		return "Rectangle";
	}
	public void addCommand(CommandStack stack) {
		for(Command cmd :list) {
			stack.addCommand(cmd);
		}
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color=color;
	}

}
