package interpreter;

import visitor.*;

public class PenDown implements Expression {
	private Turtle instance;

	public void interpret( Context commands ) {
		instance = commands.getTurtleInstance();
		instance.penDown();
	}
	
	public void undo() {
		instance.penUp();
	}
	
	public void redo() {
		instance.penDown();
	}
	
	public void accept( Visitor aGuess) {
		aGuess.visit( this );
	}
	
	public String toString() {
		return "penDown";
	}
}
