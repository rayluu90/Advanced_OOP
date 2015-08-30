package interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import exception.*;

public class Context {
	private List<String> commandContainer;
	private int commandPosition;
	private String currentCommand;
	
	private Turtle instance;
	// hold all command object for visitor
	private List<Expression> hosts;
	// hold variable of the program
	private TreeMap<String,Variable> globalVariableContainer;
	// use factory pattern to create object
	private Factory createExpression;
	
	public Context ( List<String> commands ) {
		this.commandContainer = commands;
		// new Turtle every time parse new file
		this.instance = new Turtle();
		// new container to contain all command object
		this.hosts = new ArrayList<Expression>();
		// new container to store variables
		this.globalVariableContainer = new TreeMap<String,Variable>();
		this.createExpression = new Factory( globalVariableContainer );
	}
	
	// for using in the repeat method
	// dont want to reset everything
	public Context ( List<String> commands, Turtle instance, 
					 List<Expression> hosts, 
					 TreeMap<String,Variable> globalVariableContainer ) {
		
		this( commands );
		this.instance = instance;
		this.hosts = hosts;
		this.globalVariableContainer = globalVariableContainer;
	}
	
	// create different expression object based on input command 
	public Expression produce( String command ) {
		return createExpression.produce( command );
	}
	
	public Turtle getTurtleInstance() {
		return this.instance;
	}
	
	// get command object container
	public List<Expression> getHosts() {
		return this.hosts;
	}
	
	public TreeMap<String,Variable> getVariableContainer() {
		return this.globalVariableContainer;
	}
	
	// ask if context has anymore command?
	public boolean hasNext() {
		return commandPosition < commandContainer.size();
	}
	
	// get next command
	// return null if not more command in the container 
	public String next() {
		if( !hasNext() ) return null; 
		// save current command
		return currentCommand = 
				commandContainer.get( commandPosition++ );
	}
	
	// get current command
	public String getCurrentCommand() {
		return currentCommand;
	}
	
	public int stringToInt( String toConvert ) {
		// if the conversion String is a variable
		// then search the variable container
		// else, don't search to avoid unnecessary searches
		// and performance the conversion
		 if( toConvert.charAt(0) == '$') {
			 Variable defined = globalVariableContainer.get( toConvert );
			 
			 // if cannot find in the container, 
			 // then variable is not defined, throw an exception 
			 if( defined == null )
				 throw new IllegalVariableException(
						 "Variable " + 
						 "'" + toConvert + "'" + 
						 " is not defined");
			 return defined.getValue();
		 }
		
		// if input is not a numerical number, throw an exception
		if( toConvert.matches("\\D+") )
			throw new IllegalArgumentException( 
					"Invalid value: " + 
					"'" + toConvert + "'" );
		
		// convert to integer
		int value = Integer.parseInt( toConvert );
	
		return value;
	}
		
	public String toString() {
		StringBuilder commandList = new StringBuilder();
		
		for( String command : commandContainer ) {
			commandList.append( command );
			commandList.append( " " );
		}
		return commandList.toString();
	}
}
