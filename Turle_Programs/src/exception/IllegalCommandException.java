package exception;

public class IllegalCommandException extends RuntimeException {

	private static final long serialVersionUID = -7035909604641880018L;

	public IllegalCommandException() {
		super();
	}
	
	public IllegalCommandException( String message ) {
		super( message );
	}
	
	public IllegalCommandException( String message, Throwable cause ) {
		super( message, cause );
	}
	
	public IllegalCommandException( Throwable cause ) {
		super( cause );
	}
	
}
