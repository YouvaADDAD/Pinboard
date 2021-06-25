package pobj.pinboard.editor.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.editor.EditorInterface;

public class ToolPincil implements Tool {
    private Color color;
    private GraphicsContext gc;
    public ToolPincil(GraphicsContext gc) {
    	this.gc=gc;
    }
	@Override
	public void press(EditorInterface i, MouseEvent e) {
		// TODO Auto-generated method stub
		gc.beginPath();
		gc.setStroke(this.color);
		gc.lineTo(e.getX(), e.getY());
		gc.stroke();
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		// TODO Auto-generated method stub
		gc.lineTo(e.getX(), e.getY());
        gc.stroke();
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		// TODO Auto-generated method stub
		 gc.lineTo(e.getX(), e.getY());
         gc.closePath();
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		// TODO Auto-generated method stub		
	}

	@Override
	public String getName(EditorInterface editor) {
		// TODO Auto-generated method stub
		return "Pincil";
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color=color;
	}

}
