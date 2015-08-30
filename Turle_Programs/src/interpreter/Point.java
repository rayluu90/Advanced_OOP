package interpreter;

public class Point {
	private double x, y;
	
	public Point( double x, double y) {
		this.setX(x);
		this.setY(y);
	}
	
	public double getX() {
		return x;
	}
	
	// only allow child or 
	// the class inside the packet to set value
	protected void setX( double x ) {
		this.x = x;
	}
	
	public double getY() {
		return y;
	}

	protected void setY( double y ) {
		this.y = y;
	}
	
	public String toString() {
		return "(" + this.x + ", " + this.y + ")";
	}
}
