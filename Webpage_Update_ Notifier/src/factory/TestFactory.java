package factory;

import static org.junit.Assert.*;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import org.junit.Test;

public class TestFactory {
	private final String 
		sampleURL = "http://www.eli.sdsu.edu/courses/" +
				    	"spring15/cs635/notes/index.html";
	
	private Factory factory;
	
	public TestFactory() throws IOException {
		this.factory = new Factory();
	}
	
	@Test
	// testing factory object 
	// produce valid observer object
	public void testProduceValidObserverFail() throws IOException {
		
		final String[] toCreateObservers = new String[]
				{ "mail", "transcript", "mail", "transcript" };
		
		Observable subject = 
				this.factory.produceSubject( this.sampleURL );

		// for each string observer in the list
		 for( String anObserver : toCreateObservers ) {
			Observer createdObserver = null;
			try {
				// produce observer object
				createdObserver = 
					this.factory.produceObserver(anObserver, subject );
			}
			catch( Exception e ) {
			// if any exception occur,
			// fail to create observer object
				fail();
			}
				
			// if the any toCreate observer is != 
			// created observer object name
			// the object is not created correctly
			if( createdObserver.toString() != anObserver.intern() )
				fail();
		 }
	 }

	
	@Test
	// testing factory object
	// produce invalid observer object
	public void testProduceInvalidedObserverFail() throws IOException {
		// produce an observable object
		Observable subject = factory.produceSubject( this.sampleURL );
		
		// attempt to create invalid observers
		String invalidObserver = "email";
		try{
			factory.produceObserver( invalidObserver, subject );
		}
		catch( Exception e ) {
		// since no 'email' observer in the list, 
		// exception will occur
			return;
		}
		// if no exception occur, it will fail
		fail();
	}
}
