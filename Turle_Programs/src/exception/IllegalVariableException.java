package exception;

public class IllegalVariableException extends RuntimeException {

	private static final long serialVersionUID = -5601231099653261944L;

	public IllegalVariableException() {
		super();
	}
	
	public IllegalVariableException( String message ) {
		super( message );
	}
	
	public IllegalVariableException( String message, Throwable cause ) {
		super( message, cause );
	}
	
	public IllegalVariableException( Throwable cause ) {
		super( cause );
	}
	
}
