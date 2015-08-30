package interpreter;

import java.text.DecimalFormat;

public class Turtle {
	// current location of the turtle
	private Point location;
	// isPenUp or down?
	private boolean penUp;
	// the turn direction of the turtle
	private int direction;
	
	public Turtle() {
		// set the position of the Turtle to (0,0)
		this.location = new Point( 0,0 );
	}
	
	public void move( int distance ) {
		double radians = ( direction * Math.PI ) / 180,
			   deltaY = Math.sin( radians ) * distance,
			   deltaX = Math.cos( radians ) * distance,
			   newX = location.getX() + deltaX,
			   newY = location.getY() + deltaY;
		
		DecimalFormat formatX = new DecimalFormat("0.00");
		DecimalFormat formatY = new DecimalFormat("0.00");
		
		newX = Double.parseDouble( formatX.format( newX ) );
		newY = Double.parseDouble( formatY.format( newY ) );
		
		location.setX( Math.abs( newX ) );
		location.setY( Math.abs( newY ) );
	}
	
	// turn direction of the turtle
	public void turn( int degrees ) {
		this.direction += degrees;
	}
	
	public void penUp() {
		// if already up, dont do anything
		if( penUp ) return;
		// if pen is not up, set it up
		penUp = true;
	}
	
	public void penDown() {
		// if already down, dont do anything
		if( !penUp ) return;
		// if is not down, set it down
		penUp = false;
	}
	
	public boolean isPendUp() {
		return this.penUp;
	}
	
	public int direction() {
		return direction;
	}
	
	// return the current location of the turtle
	public Point location() {
		return this.location;
	}
}
