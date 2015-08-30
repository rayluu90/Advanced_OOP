package memento;

import static org.junit.Assert.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import org.junit.Test;

public class TestCareTaker {
	private CareTaker<String> aCareTaker;
	private final String fileName = "test.txt";
	private String[] animals;
	private File aFile;
	
	public TestCareTaker() throws ClassNotFoundException {
		this.aCareTaker = new CareTaker<String>( this.fileName );
		this.animals = new String[]
				{ "bear", "dog", "horse", "tiger" };
		
		// locate the file in the directory
		this.aFile = new File( this.fileName );
	}
	
	private void addStates() throws IOException, ClassNotFoundException {
		for( String animal : animals ) 
			this.aCareTaker.addState( animal );
	}
	
	// delete any created file while performing the operation
	// then call fail
	private void deleteCreatedFile() {
		aFile.delete(); 
		fail();
	}

	@Test
	// test if file is created 
	public void testCreateFileFail() 
			throws IOException, ClassNotFoundException {
		
		// add current state and write the state to file
		addStates();
		
		// file should exist, fail if not
		if( !this.aFile.exists() ) deleteCreatedFile();
			
		// clean up
		aFile.delete();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	// after save the states, 
	// testing contents of the files
	public void testReadFileContentFail() 
			throws ClassNotFoundException, IOException {
		// write new states to file
		addStates();
		
		// locate the file
		FileInputStream file = new FileInputStream( this.fileName );
		ObjectInputStream input = new ObjectInputStream( file );
		
		LinkedList<String> statesToRetore = null;
		// attempt to read the state from the file
		statesToRetore = ( LinkedList<String> ) input.readObject();
		
		input.close();
		int position = 0;
		
		// for each state in the restored states
		for( String anAnimal: statesToRetore ) 
			// compare each state when adding each animal in
			// state 1 == animal at position 1
			// state 2 == animal at position 2 and ect...
			// delete created file if there is any error
			if( this.animals[ position++ ] != anAnimal.intern() )
				deleteCreatedFile();
		
		// delete the file after done
		this.aFile.delete();
	}
	
	@Test
	// testing if has previous state from file 
	// after write the last state to the file
	public void testHasPreviousStateSaveFail() 
			throws ClassNotFoundException, IOException {
		// add state and write to files 
		// previous state is save
		addStates();
		
		// try to look for the file to restore the state
		CareTaker<String> careTaker = 
				new CareTaker<String>( this.fileName );
		
		// attempt to check if there is previous state from a file
		boolean hasLastState = careTaker.hasPreviousStateFromFile();
		
		// if no state is save, fail
		if( !hasLastState ) deleteCreatedFile();
		
		this.aFile.delete();
	}
}
