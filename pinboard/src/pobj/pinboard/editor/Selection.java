package pobj.pinboard.editor;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import pobj.pinboard.document.Board;
import pobj.pinboard.document.Clip;

public class Selection {
    private List<Clip> list=new ArrayList<>();
    
    public void select(Board board, double x, double y) {
    	clear();
    	for(Clip e :board.getContents()) {
    		if(e.isSelected(x, y)) {
    			list.add(e);
    			return;
    		}
    	}
    }
    
    public void toogleSelect(Board board, double x, double y) {
    	for(Clip e :board.getContents()) {
    		if(e.isSelected(x, y)) {
    			if(list.contains(e)) {
    				list.remove(e);
    				return ;
    			}else {
    				list.add(e);
    				return ;
    			}
    		}
    	}
    	
    }
    public void clear() {
    	list.clear();
    }
    public List<Clip> getContents(){
    	return list;
    }
    
    public void drawFeedback(GraphicsContext gc) {
		double top = 0.0;
		double bottom = 0.0;
		double left = 0.0;
		double right = 0.0;
		if(!list.isEmpty()) {
			top = list.get(0).getTop();
			bottom = list.get(0).getBottom();
			left = list.get(0).getLeft();
			right = list.get(0).getRight();
		}
		for(Clip c : list) {
			if(c.getBottom() > bottom)
				bottom = c.getBottom();
			if(c.getTop() < top)
				top = c.getTop();
			if(c.getLeft() < left)
				left = c.getLeft();
			if(c.getRight() > right)
				right = c.getRight();
		}
		gc.strokeRect(left, top, right-left, bottom-top);
	}
    
}

