package factory;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Observable;
import java.util.Observer;
import exceptions.*;
import observer.*;

@SuppressWarnings("serial")
public class Factory implements Serializable {
// Usually, this would implemented using abstract factory pattern
// to produce different factories to produce different object for 
// achieve higher cohesion
// since this assignment is short
// it doesn't make sense to create 3 or 4 different factories and
// each factory have only have 1 method 
// so i combined all the factory into one
	
	// produce observer which observing the subject
	public Observer produceObserver
				( String toCreateObserver, Observable aSubject) {
		Observer anObserver = null;
		
		if( toCreateObserver.equals( "transcript" ) )
			anObserver = new Transcript( aSubject );
		else if( toCreateObserver.equals( "mail" ) )
			anObserver = new Mail( aSubject );
		else
			throw new IllegalObserverException( 
				"Unrecognized Observer: " +"'"+ toCreateObserver + "'");
		return anObserver;
	}
	
	// produce a subject or the object being observe 
	public Observable produceSubject( String toCreateSubject ) 
			throws IOException {
		return new Subject( toCreateSubject );
	}
	
	// produce real URL object
	public URL produceURL( String toCreateURL ) 
			throws MalformedURLException {
		return new URL( toCreateURL );
	}
	
	// produce a real URL connection URL
	public URLConnection produceURLConnection( URL anURL ) 
			throws IOException {
		return anURL.openConnection();
	}
}
