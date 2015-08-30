package interpreter;

import visitor.*;

public class Move implements Expression {
	private int distance;
	private Turtle instance;
	private boolean isUndo;
	
	public void interpret( Context commands ) {
		instance = commands.getTurtleInstance();
		
		// after Move, next command should be a numeric value
		String distanceUnit = commands.next();
		
		// convert from String to int
		distance = commands.stringToInt( distanceUnit );
		
		// move the turtle
		instance.move( distance );
	}
	
	// accept visitor
	public void accept( Visitor aGuess) {
		aGuess.visit( this );
	}
	
	public int getDistance() {
		return distance;
	}
	
	public String toString() {
		return "move";
	}
	
	public void undo() {
		// do nothing if already executed undo
		if( isUndo) return;
		instance.move( -distance );
		isUndo = true;
	}
	
	public void redo(){
		// do nothing if already executed redo
		if( !isUndo ) return;
		instance.move( distance );
		isUndo = false;
	}	
}
