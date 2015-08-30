package visitor;

import java.util.Stack;
import interpreter.*;

public class CommandVistorManager {
	private Stack<Expression> undoCommandContainer, redoCommandContainer;
	
	public CommandVistorManager( Stack<Expression> commands ) {
		this.undoCommandContainer = commands;
		this.redoCommandContainer = new Stack<Expression>();
	}
	
	// call this constructor 
	// if want to restore the state of the turtle to (0,0)
	public CommandVistorManager( 
			Stack<Expression> commands, boolean isRestoreState) {
		
		this( commands );
		// undo all the commands
		while( !undoCommandContainer.isEmpty() ) 
			undo();
	}
	
	public boolean isUndoContainerEmpty() {
		return undoCommandContainer.isEmpty();
	}
	
	public boolean isRedoContainerEmpty() {
		return redoCommandContainer.isEmpty();
	}
	
	public void execute() {		
		// when redo is called,
		// pop the object out of redo container 
		Expression removedCommand = redoCommandContainer.pop();
		
		// call the redo method of the object
		// to redo a part of the program
		removedCommand.redo();
		
		// push the object to the undo container
		undoCommandContainer.push( removedCommand );
	}
	
	public void undo() {
		// when undo is called,
		// pop the object out of undo container 
		Expression removedCommand = undoCommandContainer.pop();
		
		// call the undo method of the object and 
		// to undo a part of the program
		removedCommand.undo();
		
		// push the object to the redo container
		redoCommandContainer.push( removedCommand );
	}
}
