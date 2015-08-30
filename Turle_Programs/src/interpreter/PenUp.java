package interpreter;

import visitor.*;

public class PenUp implements Expression {
	private Turtle instance;

	public void interpret( Context commands ) {
		instance = commands.getTurtleInstance();
		instance.penUp();
	}
	
	public void undo(){
		instance.penDown();
	}
	
	public void redo(){
		instance.penUp();
	}
	
	public void accept( Visitor aGuess) {
		aGuess.visit( this );
	}

	public String toString() {
		return "penUp";
	}
}
