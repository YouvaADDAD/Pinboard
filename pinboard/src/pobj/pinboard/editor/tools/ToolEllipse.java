package pobj.pinboard.editor.tools;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.document.Clip;
import pobj.pinboard.document.ClipEllipse;
import pobj.pinboard.editor.CommandStack;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.editor.commands.Command;
import pobj.pinboard.editor.commands.CommandAdd;

	public class ToolEllipse implements Tool {
		   private Clip ellipse;
		   private double press_x;
		   private double press_y;
		   private double release_x;
		   private double release_y;
		   private Color color;
		  
		   private List<Command> list=new ArrayList<>();
			@Override
			public void press(EditorInterface i, MouseEvent e) {
				press_x=e.getX();
				press_y=e.getY();
				ellipse = new ClipEllipse(press_x, press_y, press_x, press_y,this.color);
				
			}

			@Override
			public void drag(EditorInterface i, MouseEvent e) {
				if(ellipse.getLeft() > e.getX() || ellipse.getTop() > e.getY()){
					ellipse.setGeometry(e.getX(),e.getY(), ellipse.getRight(),ellipse.getBottom());
				}else {
					ellipse.setGeometry(ellipse.getLeft(),ellipse.getTop(), e.getX(),e.getY()); 
				}
			}

			@Override
			public void release(EditorInterface i, MouseEvent e) {
				release_x=e.getX();
				release_y=e.getY();
				
				if(press_x > release_x || press_y > release_y){
					ellipse.setGeometry(release_x,release_y, press_x,press_y);
				}else {
					ellipse.setGeometry(press_x,press_y, release_x,release_y); 
				}
				CommandAdd cv=new CommandAdd(i,ellipse);
				list.add(cv);
				cv.execute();
				
			}

			@Override
			public void drawFeedback(EditorInterface i, GraphicsContext gc) {
				gc.setStroke(this.color);
				gc.strokeOval(ellipse.getLeft(), ellipse.getTop(),ellipse.getRight()-ellipse.getLeft(),ellipse.getBottom()-ellipse.getTop());
				
			}

			@Override
			public String getName(EditorInterface editor) {
				// TODO Auto-generated method stub
				return "Ellipse";
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
				this.color = color;
			}
			
}
