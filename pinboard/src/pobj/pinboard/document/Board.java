package pobj.pinboard.document;


import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Board  {
   
	List<Clip> list=new ArrayList<>();
	
	public List<Clip> getContents(){
		return list;
	}
	public void addClip(Clip clip) {
		list.add(clip);
	}
	public void addClip(List<Clip> clip) {
		list.addAll(clip);
	}
	public void removeClip(Clip clip) {
		list.remove(clip);
	}
	public void removeClip(List<Clip> clip) {
		list.removeAll(clip);
	}
	public void draw(GraphicsContext gc) {
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
		for(Clip e:list) {
			e.draw(gc);
		}
	}

}
