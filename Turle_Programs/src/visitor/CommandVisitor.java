package visitor;

import java.util.Stack;
import interpreter.*;

public class CommandVisitor implements Visitor {
	// create a stack to hold command object
	private Stack<Expression> commandsContainer;
	
	public CommandVisitor() {
		this.commandsContainer = new Stack<Expression>();
	}
	
	public void visit( Move move ) {
		commandsContainer.push( move );
	}

	public void visit( Turn turn ) {
		commandsContainer.push( turn );
	}

	public void visit( Repeat repeat ) {
		commandsContainer.push( repeat );
	}
	
	public void visit( PenUp penUp ) {
		commandsContainer.push( penUp );
	}

	public void visit( PenDown penDown ) {
		commandsContainer.push( penDown );
	}

	public void visit( Program program ) {}
	
	public Stack<Expression> getCommands() {
		return this.commandsContainer;
	}
}
