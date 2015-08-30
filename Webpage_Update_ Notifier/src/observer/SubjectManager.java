package observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.io.IOException;
import factory.*;
import memento.*;

// create subject and observer 
public class SubjectManager {
	private List<Observable> subjectContainer;
	private CareTaker<Memento<Observable>> savedStateContainer;
	
	public SubjectManager( Map<String,List<String>> subjects ) 
						throws IOException, ClassNotFoundException { 
		this.savedStateContainer = new CareTaker<Memento<Observable>>();
				
		// if there is last state to restore 
		// restore last state from file, set current state = last state 
		if( this.savedStateContainer.hasPreviousStateFromFile() ) 
			setStateFromMomento( 
					this.savedStateContainer.getLastState() );
		else {
			// if nothing to restore, set up new state
			this.subjectContainer = new ArrayList<Observable>();
			
			// set new state by passing in:
			// a container to store all subjects object and
			// and a Map contains subjects and observers
			setNewState( this.subjectContainer, subjects );
		}
	}
	
	protected void setNewState( List<Observable> subjectContainer,
				Map<String,List<String>> subjects  ) throws IOException {
		
		// factory to create subject and observers
		Factory factory = new Factory();
		
		// for each key in the Map
		for( String key : subjects.keySet() ) {
			// create a subject based on each key
			Observable aSubject = factory.produceSubject( key );			
			// add the subject to the container
			subjectContainer.add( aSubject );
			
			// get the list of observers associate with the subject
			List<String> observers = subjects.get( key );
			
			// create observer object for each URL
			for( String anObserver : observers ) 
				factory.produceObserver( anObserver, aSubject );
		}
	}
	
	// check for changes for each subject 
	// if any subject changes its state,
	// notify its observers
	public void checkSubjectsForChanges() throws IOException {
		boolean isSaveState = false;
		
		while( true ) {
			for( Observable aSubject : this.subjectContainer ) 
				if( aSubject.hasChanged() ) {
					// Subject changed its state
					// set the flag to save the current state
					if( !isSaveState ) isSaveState = true;
					// notify observers
					aSubject.notifyObservers();
				}
			
			if( isSaveState ) { 
				// save current state
				saveStateToMomento();
				// state is saved, switch the flag to false
				isSaveState = false;
			}
		}	
	}
	
	protected void saveStateToMomento() throws IOException {
		// create momento to save current state
		Memento<Observable> currentState = 
				new Memento<Observable>( this.subjectContainer );
		// give it to care taker
		this.savedStateContainer.addState( currentState );
	}
	
	// restore state from the care taker
	protected void setStateFromMomento( Memento<Observable> input ) {
		this.subjectContainer = input.getState();
	}
}
