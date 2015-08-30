package interpreter;

import java.util.List;

import visitor.*;

public class Program implements Expression {
	// list of commandObject for visitor
	private List<Expression> hosts;	
	
	public void interpret( Context commands) {
		hosts = commands.getHosts();
		
		// starting point, iterate through the list of parsed command 
		while( commands.hasNext() ){
			// get next command
			String command = commands.next();
			
			// create each object base on what type of the command 
			Expression commandObject = commands.produce( command );
				
			// add each command object to the list
			hosts.add( commandObject );
			
			// call interpret of each object returned by the factory
			commandObject.interpret( commands );			
		}
	}
	
	// accept visitor and allow to visit
	public void accept( Visitor aGuess) {
		// iterator through the list of command object
		for( Expression host : hosts )
			// call accept visitor on each command object
			host.accept( aGuess );
		aGuess.visit( this );
	}
	
	public String toString() {
		StringBuilder commandList = new StringBuilder();

		for( Expression host : hosts ) {
			commandList.append( host.toString() );
			commandList.append("\n");
		}
		return commandList.toString();	
	}
	
	public void undo(){}
	public void redo(){}
}
