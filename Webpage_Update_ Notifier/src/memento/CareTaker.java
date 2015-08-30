package memento;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

// implement generic so care taker can save any type
public class CareTaker<E>  {
	// mimic the stack using a LinkedList
	private LinkedList<E> savedStateContainer;
	// check if previous state exist
	private boolean hasPreviousState;
	private static final String defaultFile = "states.txt";
	private String fileName;
	
	public CareTaker() throws ClassNotFoundException  {
		// default file to to write and save state
		// if not specified
		this( defaultFile );
	}
	
	@SuppressWarnings("unchecked")
	public CareTaker( String inputFile ) throws ClassNotFoundException  {
		this.fileName = inputFile;
		
		try {
			// attempt to read object from the file
			FileInputStream file = new FileInputStream( this.fileName );
			ObjectInputStream input = new ObjectInputStream( file );
  
		    try {
				// if file exist, restore the states
		    	// reconstructing all objects
		    	// since return will be pure Object, 
		    	// so cast it to specific type
		    	// LinkedList in this case
				this.savedStateContainer = 
						( LinkedList<E> ) input.readObject();
		    }
		    finally {
		    	input.close();
		    }
		    // set flag indicate state need to restore from file
			this.hasPreviousState = true;
			
		} catch ( IOException e ) {
			// if file not found, 
			// create new LinkedList to store new states
			this.savedStateContainer = new LinkedList<E>();
		}	
	}
	
	public void addState( E stateToSave ) throws IOException {
		// if the size of the list is 30 or more
		// discard the first state in the list
		if( this.savedStateContainer.size() > 30 ) 
			this.savedStateContainer.removeFirst();
		
		// save state to a container
		this.savedStateContainer.addLast( stateToSave );
		
		// attempt to create new files
		FileOutputStream file = new FileOutputStream( this.fileName );
		ObjectOutputStream output = new ObjectOutputStream( file );
		
		// write current state to file
		output.writeObject( this.savedStateContainer );
		
		output.flush();
		output.close();
	}
	
	// has the last state saved from an external file?
	public boolean hasPreviousStateFromFile() {
		return this.hasPreviousState;
	}
	
	// get the last state from in the list 
	public E getLastState() {
		// get the very last state in the list
		return this.savedStateContainer.removeLast();
	}
}
