package observer;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import factory.Factory;
import factory.MockFactory;
import static org.mockito.Mockito.*;

@SuppressWarnings("serial")
public class Subject extends Observable implements Serializable  {
	// store all observers
	private List<Observer> container;
	private long currentModifiedTime;
	private URL anURL;
	private Factory factory;
	
	public Subject( String inputURL ) throws IOException {	
		
		//------------MOCK FACTORY---------
		//----USE TO CREATE MOCK OBJECT FOR TESTING----
		// using factory to produce mock object
		// use for testing purposes only
		this.factory = new MockFactory();
		//--------------REMOVE WHEN DONE-------------

		
		//--------- REAL FACTORY---------
		// create a factory to produce object
//		this.factory = new Factory();
		//----UNCOMMENT WHEN DONE TESTING -------
		
		// produce URL object
		this.anURL = this.factory.produceURL( inputURL );
		
		// get the last modified of the webpage
		this.currentModifiedTime = getLastModified();

		// store all the observers in a container
		this.container = new ArrayList<Observer>();		
	}
	
	public void addObserver( Observer toAdd ) {
		this.container.add( toAdd );
	}
	
	public void deleteObservers() {
		this.container.clear();
	}
	
	public int countObservers() {
		return this.container.size();
	}
	
	public boolean hasChanged() {
		// get the time the website last modified
		long lastModified = getLastModified();

		// if the current saved time is after or = to last modified
		// the site has no changes at all
		if( this.currentModifiedTime >= lastModified )
        	return false;
		
        this.currentModifiedTime = lastModified;
		return true;
	}
	
	private long getLastModified() {
		URLConnection connect = null;		
		try {
			// produce URL Connection object
			// pass in the url object in 
			connect = this.factory.produceURLConnection( this.anURL );
		} catch (IOException e) {
		// exist and print out the error if catch the exception
			e.printStackTrace();
			System.exit(0);
		}
		
		//--------- TESTING USING MOCK OBJECT---------
		// for testing purposes only, remove when done
		when( connect.getLastModified() )
			.thenReturn( this.currentModifiedTime + 1000 );
		//--------------REMOVE WHEN DONE-------------
		
		// get the last modified time  
		// from the url connection object
		return connect.getLastModified();
	}
	
	public void notifyObservers() {
		for( Observer anObsever :  this.container )
			anObsever.update( this, 
					new Date( this.currentModifiedTime ) );
	}
	
	public String toString() {
		return this.anURL.toString();
	}
}
