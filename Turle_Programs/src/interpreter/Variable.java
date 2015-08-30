package interpreter;

import visitor.*;

public class Variable implements Expression {
	// save the value of the variable and retrieve later 
	private int variableValue;
	// save variable name for this variable object
	private String variableName;
	
	public void interpret( Context context ) {
		// save variable name to identify 
		// this variable object in toString method
		variableName = context.getCurrentCommand();
		
		// get next command
		String command = context.next();
		
		// is the current command equal_character?
		// go to the next command and
		// it should be a numeric value
		if( command.equals("=") ) 
			command = context.next();
		
		// is variable already defined? OR 
		// assigning new value to a defined variable?
		if( variableValue == 0 || 
				( !String.valueOf( variableValue ).equals( command ) ) )
			// send the String numeric value to the context
			// to convert to int
			variableValue = context.stringToInt( command );		
	}
	
	// get value of the variable
	public int getValue() {
		return variableValue;
	}
	
	public void accept( Visitor aGuess ) {}
	
	public String toString() {
		return variableName;
	}
	
	public void undo(){}
	public void redo(){}
}
