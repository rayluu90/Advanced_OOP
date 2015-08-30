package observer;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("serial")
public class Transcript implements Observer, Serializable {
	
	public Transcript( Observable aSubject ) {
		aSubject.addObserver( this );
	}
	
	public void update( Observable aSubject, Object aMessage ) {
		// print out the console
		System.out.println( "URL '" + aSubject.toString() + 
			"' has modified its contents at " + aMessage.toString() );
	}
	
	public String toString() {
		return "transcript";
	}
}
