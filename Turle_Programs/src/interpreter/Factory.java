package interpreter;

import java.util.TreeMap;
import exception.*;

public class Factory {
	private TreeMap<String,Variable> globalVariableContainer;

	public Factory( TreeMap<String,Variable> globalVariableContainer ) {
		this.globalVariableContainer = globalVariableContainer;
	}
	
	public Expression produce( String command ) {
		Expression created = null;

		if( command.equals("move") ) 
			created = new Move();

		else if( command.equals("turn") ) 
			created = new Turn();
		
		else if( command.equals("penUp"))
			created = new PenUp();
		
		else if( command.equals("penDown") )
			created = new PenDown();
		
		else if( command.equals("repeat") )
			created = new Repeat();
		
		// if the first char is '$', then it is a variable
		else if( command.charAt(0) == '$' ) {
			// if it is only $, then variable name is missing
			// throw an exception
			if( command.length() == 1 )
				throw new IllegalVariableException(
						"Missing Variable Name after: " + command );
			
			// is current variable defined?
			if( !globalVariableContainer.containsKey( command ) ) 
				// put a variable object associate with the command
				globalVariableContainer.put( command, new Variable() );
			
			// get the object
			created = globalVariableContainer.get( command );
		}
		else 
		// invalid command
			throw new IllegalCommandException(
					"Invalid command: " + "'" + command + "'" );
		
		return created; 
	}
}
