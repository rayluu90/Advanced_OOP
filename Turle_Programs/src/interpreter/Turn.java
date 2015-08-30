package interpreter;

import visitor.*;

public class Turn implements Expression {
	private int degree;
	private Turtle instance;
	private boolean isUndo; 
	
	public void interpret( Context commands ) {
		instance = commands.getTurtleInstance();
		// after Turn command, should be a numeric number
		String degreeUnit = commands.next();
		// convert, String to int
		degree = commands.stringToInt( degreeUnit );
		// call Turtle class
		instance.turn( degree );
	}
	
	public void accept( Visitor aGuess) {
		aGuess.visit( this );
	}
	
	public String toString() {
		return "turn";
	}
	
	public void undo() {
		// do nothing if already executed undo
		if( isUndo) return;
		instance.turn( -degree );
		isUndo = true;
	}
	
	public void redo() {
		// do nothing if already executed redo
		if( !isUndo ) return;
		instance.turn( degree );
		isUndo = false;
	}
}
