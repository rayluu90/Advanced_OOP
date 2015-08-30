package observer;

import static org.junit.Assert.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import org.junit.Test;

public class TestObserverObservable {
	private final String 
			testFile = "file.txt",
			sampleURL = "http://www.eli.sdsu.edu/courses/" +
						"spring15/cs635/notes/index.html";
	private Parse fileContents;

	public TestObserverObservable() throws IOException {
		this.fileContents = new Parse( testFile );		
	}
	
	@Test
	// testing on the Parse object 
	// parse file 
	public void testParseFileFail() {
		 Map<String,List<String>> container = 
				 this.fileContents.getSubjects();
		 // for each key in all of the key set
		 for( String key : container.keySet() ) {
			 // get the list of observers 
			 // associate with each website
			 List<String> observers = container.get( key );
			 // in each observer
			 for( String observer : observers )
				 // if any observer is empty
				 // doesn't parse correctly
				 if( observer.isEmpty() ) fail();
		 }
	}
	
	@Test
	// testing Observable class
	// hasChanged method
	public void testHasChangedFail() throws IOException {
		// create subject using factory
		Observable subject = new Subject( this.sampleURL );
		
		// testing hasChanged method
		boolean isChanged = subject.hasChanged();
		assertTrue( isChanged );
	}
	
	@Test
	// testing SubjectManager
	// setNewState method
	public void testingSetNewStateFail() 
			throws ClassNotFoundException, IOException {
		
		// create a list to container observers 
		List<String> observerContainer = new ArrayList<String>();
		String sampleObserver = "transcript";
		// add the sample observer to the list
		observerContainer.add(sampleObserver);
		
		// create a map which contain:
		// a subject and a list of observer associate with subject
		Map<String,List<String>> subjects = 
				new HashMap<String,List<String>>();
		subjects.put( this.sampleURL, observerContainer );
		
		// create an empty list to contain subject
		List<Observable>subjectContainer = new ArrayList<Observable>();
		// testing set new state in subject manager class
		SubjectManager manager = new SubjectManager( subjects );
		
		// set new state
		manager.setNewState( subjectContainer, subjects );
		
		// get the first subject in the list 
		// should have some observer associate with it 
		Observable aSubject = subjectContainer.get(0);
		
		// there should be one observer in the subject
		// fail if its not
		assertEquals( aSubject.countObservers(), 1 );
	}
	
	@Test 
	public void tesMailObserverFail() throws IOException {
		// create observable object or subject being observe
		Observable aSubject = new Subject( this.sampleURL );
		Mail mail = new Mail( aSubject );
		// testing update method on the mail object
		mail.update( aSubject, "JUnit Testing" );
		
		// check if the mail is successfully sent
		assertTrue( mail.isSendSuccess() );
	}
}
