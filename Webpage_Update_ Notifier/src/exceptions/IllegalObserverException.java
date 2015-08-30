package exceptions;

@SuppressWarnings("serial")
public class IllegalObserverException extends RuntimeException {
	
	public IllegalObserverException() {
		super();
	}
	
	public IllegalObserverException( String message ) {
		super( message );
	}
	
	public IllegalObserverException( String message, Throwable cause ) {
		super( message, cause );
	}
	
	public IllegalObserverException( Throwable cause ) {
		super( cause );
	}
	
}
