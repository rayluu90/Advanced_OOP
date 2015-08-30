import static org.junit.Assert.*;

import java.util.List;
import java.util.Stack;
import java.util.TreeMap;

import org.junit.Test;

import visitor.*;
import interpreter.*;

public class TestCommands {
	final String simpleTestFile = "simple.txt";
	final String complexTestFile = "complex.txt";
	Context simpleCommands;
	Context complexCommands; 
	Program program;

	public TestCommands() {
		this.simpleCommands = new Context(
				parseFile( simpleTestFile ) );
		this.complexCommands = new Context( 
				parseFile( complexTestFile ) );
		this.program = new Program(); 
	}
	
	public List<String> parseFile( String fileName ) {
		Parse file = null;
		try {
			file = new Parse( fileName );
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		// return a container contains all the parsed command
		return file.getCommandContainer();
	}
	
	@Test
	public void testParsingFileFail() {
		List<String> commandContainer = 
							parseFile( complexTestFile );
		// if any command is empty after parse, 
		// empty string will not be processed correctly
		for( String command : commandContainer ) 
			if( command.isEmpty() ) 
				fail();
		}
	
	@Test
	public void testChangeVariableValueFail() {
		/*	$variable = 5
			move $variable
			$variable = 10
			move $variable	*/
		program.interpret( simpleCommands );
		
		List<Expression> commandContainer = 
				simpleCommands.getHosts();
		
		TreeMap<String,Variable> variableContainer = 
				simpleCommands.getVariableContainer();
		
		// get the variable before changes
		Expression beforeChanged = (Variable) commandContainer.get(0);
		
		// get the variable after changed from the variable container
		// using the same variable name
		Variable afterChanged = 
				variableContainer.get( beforeChanged.toString() );
		
		// if changed variable value is not 10, 
		// then variable is not changed
		assertEquals( afterChanged.getValue(), 10 );
	}
	
	@Test 
	public void testSetDefinedToNewVariableFail() {
		/*
		 Example: $variable = 10
		 		  $newVariable = $variable
		 		  move $newVariable	
		 */		
		program.interpret( simpleCommands );

		TreeMap<String,Variable> variableContainer = 
				simpleCommands.getVariableContainer();
				
		Variable oldVariable = variableContainer.get( "$variable" );
		Variable newVariable = variableContainer.get( "$newVariable" );
		
		// if both new and old variable are not the same
		// set old variable to new variable fail
		assertEquals( oldVariable.getValue(), newVariable.getValue() );
	}
	
	@Test
	public void testMoveFail() {
		program.interpret( simpleCommands );

		// get the current location of the Turtle
		Point location = simpleCommands.getTurtleInstance().location();	
		// the Turtle should traveled 15 units
		
		double traveledDistance = location.getY();
		// if the distance travel is not 15, 
		// then the move command fail
		
		assertEquals( (int) traveledDistance, 15 );
	}
	
	@Test
	public void testTurnFail() {
		program.interpret( simpleCommands );

		// after parsed and interpreted the program in the constructor
		// the direction of the Turtle should be 90
		int direction = simpleCommands.getTurtleInstance().direction();
		
		// fail if the direction of the Turtle is not 90
		assertEquals( direction, 90 );
		
		// additional test to make sure 
		// the Turn command is processed correctly
		Point location = simpleCommands.getTurtleInstance().location();
		double traveledDistance = location.getY();
		
		// since the turtle turned to 90 degree before moved,
		// the position of the Turtle should be at (0,15)
		// if the Y position is not at 15, the turn command fail
		assertEquals( (int) traveledDistance, 15 );
	}
	
	@Test
	public void testPenUpFail() {
		program.interpret( simpleCommands );

		// if pen is still down after execute penUp command, 
		// then execute command fail
		assertTrue( simpleCommands.getTurtleInstance().isPendUp() );
	}
	
	@Test
	public void testCommandVisitorFail() {	
		program.interpret( simpleCommands );

		CommandVisitor commandsGuess = new CommandVisitor();
		// send a visitor to the program
		program.accept( commandsGuess );
		
		// get back a list of commands
		Stack<Expression> commandContainer = commandsGuess.getCommands();
		
		// all the command in the test file
		String[] testCommands = { "penUp", "turn", "move", "move" };
		int commandPosition = 0;
		
		// iterator through all the command object 
		// in the command container
		for( Expression command : commandContainer ) {
			String commandName = command.toString(),
				   testCommand = testCommands[ commandPosition++ ];
			
			// if the commandOject in the commandContainer != testCommand
			// visitor fail
			assertEquals( commandName, testCommand );
		}
	}
	
	// use this method to reset the turtle 
	// back to original position (0,0)
	// to help performance redo and undo
	private CommandVistorManager redoUnoHelper( boolean isRestoreState) {
		CommandVisitor commandsGuess = new CommandVisitor();
		// send a visitor
		program.accept( commandsGuess );
		
		// get back a list of commands
		// the commands should be in reversed order
		// move, move, turn, penUp
		Stack<Expression> commandContainer = 
				commandsGuess.getCommands();
		
		if( isRestoreState )
			return new CommandVistorManager( 
					commandContainer, isRestoreState );
		
		// get the list of command for redo returned by the visitor
		// undo all of the operations to reset 
		// the turtle to (0,0) position
		// redo container should be empty
		return new CommandVistorManager( commandContainer );
	}
	
	@Test
	public void testRedoFail() {
		program.interpret( simpleCommands );

		boolean isRestoreState = true;
		
		// call redoUnoHelper to reset the turtle to position (0,0)
		// and test redo method of each Expression object
		CommandVistorManager currentCommand = 
						redoUnoHelper( isRestoreState );
		
		Turtle instance = simpleCommands.getTurtleInstance();
		Point turtleLocation = instance.location();
		
		// execute penUp command
		currentCommand.execute();
		assertTrue( instance.isPendUp() );
		
		// execute turn 90
		currentCommand.execute();
		assertEquals( instance.direction(), 90 );
		
		// execute move 5
		currentCommand.execute();
		assertEquals( (int) turtleLocation.getY(), 5 );
		
		// execute move 10
		currentCommand.execute();
		assertEquals( (int) turtleLocation.getY(), 15);
	}
	
	@Test
	public void testUndoFail() {
		program.interpret( simpleCommands );

		// dont want to restore the turtle state to (0,0) 
		// use for undo
		boolean isRestoreState = false;
		 
		CommandVistorManager currentCommand = 
						redoUnoHelper( isRestoreState );
		
		Turtle instance = simpleCommands.getTurtleInstance();
		Point location = instance.location();
		
		// before undo, the turtle should be at (0,15)
		assertEquals( (int) location.getX(), 0 );
		assertEquals( (int) location.getY(), 15 );
		
		// start to undo
		currentCommand.undo();
		// turtle should be at (0,5)

		assertEquals( (int) location.getX(), 0 );
		assertEquals( (int) location.getY(), 5 );
		
		currentCommand.undo();
		// turtle should be at (0,0)
		assertEquals( (int) location.getX(), 0 );
		assertEquals( (int) location.getY(), 0 );
				
		// turtle direction should be at 90 before undo
		currentCommand.undo();
		// turtle direction should be at 0 after undo

		assertEquals( (int) instance.direction(), 0 );
		
		// pen Should be up before undo
		currentCommand.undo();
		// pen Should be down after undo
		assertFalse( instance.isPendUp() );
	}
	
	@Test
	public void testPenDownFail() {
		program.interpret( complexCommands );
				
		// if pen is still down after execute penUp command, 
		// then execute command fail
		assertFalse( complexCommands.getTurtleInstance().isPendUp() );
	}
	
	@Test
	public void testRepeatFail() {
		program.interpret( complexCommands );
		
		// interpret new set of commands
		program.interpret( complexCommands );
		
		Point turtleLocation = 
				complexCommands.getTurtleInstance().location();
		double xPostion = turtleLocation.getX(),
			   yPosition = turtleLocation.getY();
		
		// before the first turn, 
		// the turtle should traveled 10 units
		assertEquals( (int) xPostion, 10 );

		// after turned 90, 
		// 3 repeat loops executed and moved turtle
		// the Turtle should end up at y position 100
		assertEquals( (int) yPosition, 100 );
	}
	
	@Test
	public void testDistanceVistorFail() {
		program.interpret( complexCommands );
		
		DistanceVisitor distanceGuess = new DistanceVisitor();
	
		// send a distance vistor to gather up all the distance
		// traveled by the turtle
		program.accept( distanceGuess );
		
		int distance = distanceGuess.getDistance();
		
		// the turtle should traveled 110 units in the complex file
		assertEquals( distance, 110);
	}
}
