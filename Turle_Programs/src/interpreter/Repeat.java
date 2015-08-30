package interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import exception.*;
import visitor.*;

public class Repeat implements Expression {
	// keep track of count statement and repeat statement
	private int endCount, repeatCount;
	
	private Turtle instance;
	private TreeMap<String,Variable> globalVariableContainer;
	private List<Expression> hosts;	

	public Repeat() {
		// already seen a repeat statement
		// automatic assume there will be an end statement
		this.endCount++;		
	}
	
	public void interpret( Context commands ) {
		instance = commands.getTurtleInstance();
		globalVariableContainer = commands.getVariableContainer();
		hosts = commands.getHosts();

		// find how many time the loop is going to repeat
		String loopValue = commands.next();
		
		// convert the loop count to numeric value
		int loopCount = commands.stringToInt( loopValue );

		// hold all commands in the loop
		List<String> commandsInLoop = new ArrayList<String>();
		
		// using recursion to find out all commands in loop.
		// since the commands in the repeat loop are 
		// going to be the same every time,
		// add all commands in the loop to the 
		// container so it can be reused
		repeatHelper( commandsInLoop, commands );

		// if # of end != # of repeat, throw exception
		if( repeatCount != endCount) 
			throw new LoopException("Missing an 'end' statement");
		
	
		// run the loop base on the # of repeat count
		while( loopCount-- > 0) {
			// pass in the current:
			// turtle instance, command object container
			// variable container and 
			// a list of commands in the repeat loop 
			// to performance execution on the turtle
			Context commandsContainer = 
					new Context( commandsInLoop, instance, 
								 hosts, globalVariableContainer );
			
			Program program = new Program();
			
			// interpret all commands in the loop
			program.interpret( commandsContainer );
		}
	}
	
	// apply recursion to read through all the commands 
	// inside a repeat loop and store it 
	private void repeatHelper(
			List<String> commandContainer, Context commands ) {
		// get next command
		String command = commands.next();
		
		// base case, is command null?
		if ( command == null ) return;
			
		// is an end statement?
		if( command.equals( "end" ) ) {
			// if total endCount is 1 left
			// endCount will decrement to 0 before exit 
			if( this.endCount-- == 1 ) return;
			
			// else, adjust the repeat count if see an end statement
			this.repeatCount--;
		}
		
		// is a repeat command?
		if ( command.equals( "repeat" ) ) {
			// increment repeat and end count
			this.endCount++; this.repeatCount++;
		}
		
		// add the commands to the the list
		commandContainer.add( command );
		
		// recurse 
		repeatHelper( commandContainer, commands );
	}
	
	public void accept( Visitor aGuess) {
		aGuess.visit( this );
	}
	
	public String toString() {
		return "repeat";
	}
	
	public void undo(){}
	public void redo() {}
}
