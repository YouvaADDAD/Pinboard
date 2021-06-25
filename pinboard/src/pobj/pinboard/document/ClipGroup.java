package pobj.pinboard.document;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ClipGroup  implements Composite{
 
	List<Clip> list=new ArrayList<>();
	@Override
	
	public void draw(GraphicsContext ctx) {
		// TODO Auto-generated method stub
		ctx.strokeRect(getLeft(), getTop(), getRight()-getLeft(), getBottom()-getTop());
		for(Clip e :list) {
			e.draw(ctx);
		}
	}

	@Override
	public double getTop() {
		// TODO Auto-generated method stub
		double top=list.get(0).getTop();
		for(Clip e :list) {
			if(e.getTop()<top) {
				top=e.getTop();
			}
		}
		return top;
	}

	@Override
	public double getLeft() {
		// TODO Auto-generated method stub
		double left=list.get(0).getLeft();
		for(Clip e :list) {
			if(e.getLeft()<left) {
				left=e.getLeft();
			}
		}
		return left;
	}

	@Override
	public double getBottom() {
		// TODO Auto-generated method stub
		double bottom=list.get(0).getBottom();
		for(Clip e :list) {
			if(e.getBottom()>bottom) {
				bottom=e.getBottom();
			}
		}
		return bottom;
	}

	@Override
	public double getRight() {
		// TODO Auto-generated method stub
		double right=list.get(0).getRight();
		for(Clip e :list) {
			if(e.getRight()>right) {
				right=e.getRight();
			}
		}
		return right;
	}

	@Override
	public void setGeometry(double left, double top, double right, double bottom) {
		// TODO Auto-generated method stub
	    move(right-left,bottom-top);
	}

	@Override
	public void move(double x, double y) {
		// TODO Auto-generated method stub
		for(Clip e :list) {
			e.move(x, y);
		}
		
	}

	@Override
	public boolean isSelected(double x, double y) {
		// TODO Auto-generated method stub
		return ((x >= getLeft() && x <= getRight()) && (y >= getTop() && y <= getBottom())) ;
	}

	@Override
	public void setColor(Color c) {
		// TODO Auto-generated method stub
		for(Clip e :list) {
			e.setColor(c);;
		}
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return list.get(0).getColor();
	}

	@Override
	public Clip copy() {
		// TODO Auto-generated method stub
		ClipGroup copy=new ClipGroup();
		for(Clip e : list) {
			copy.addClip(e.copy());
		}
		return copy;
	}

	@Override
	public List<Clip> getClips() {
		// TODO Auto-generated method stub
		return list;
	}

	@Override
	public void addClip(Clip toAdd) {
		// TODO Auto-generated method stub
		
		list.add(toAdd);
		
		
	}

	@Override
	public void removeClip(Clip toRemove) {
		// TODO Auto-generated method stub
		
		list.remove(toRemove);
	}

}
