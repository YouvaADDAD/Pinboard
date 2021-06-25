package pobj.pinboard.document;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

abstract public class AbstractClip implements Clip {

	
	private double left;
	private double top;
	private double right;
	private double bottom;
	private Color color;
    public AbstractClip (double left, double top, double right, double bottom, Color color) {
    	this.left=left;
    	this.right=right;
    	this.top=top;
    	this.bottom=bottom;
    	this.color=color;
    }
	@Override
	abstract public void draw(GraphicsContext ctx) ;

	@Override
	public double getTop() {
		// TODO Auto-generated method stub
		return top;
	}

	@Override
	public double getLeft() {
		// TODO Auto-generated method stub
		return left;
	}

	@Override
	public double getBottom() {
		// TODO Auto-generated method stub
		return bottom;
	}

	@Override
	public double getRight() {
		// TODO Auto-generated method stub
		return right;
	}
	public double getHeight() {
		return bottom-top;
	}
	public double getWeight() {
		return right-left;
	}

	@Override
	public void setGeometry(double left, double top, double right, double bottom) {
		// TODO Auto-generated method stub
		this.left=left;
    	this.right=right;
    	this.top=top;
    	this.bottom=bottom;
	}

	@Override
	public void move(double x, double y) {
		// TODO Auto-generated method stub
		this.left=left+x;
    	this.right=right+x;
    	this.top=top+y;
    	this.bottom=bottom+y;
	}

	@Override
	public boolean isSelected(double x, double y) {
		// TODO Auto-generated method stub
		return ((x >= left && x <= right) && (y >= top && y <= bottom)) ;
			
	}

	@Override
	public void setColor(Color c) {
		// TODO Auto-generated method stub
		this.color=c;
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return color;
	}

	
	abstract public Clip copy() ;
}
