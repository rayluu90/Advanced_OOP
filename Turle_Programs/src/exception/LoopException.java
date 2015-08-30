package exception;

public class LoopException extends RuntimeException {
	
	private static final long serialVersionUID = -5884870905059396920L;

	public LoopException() {
		super();
	}
	
	public LoopException( String message ) {
		super( message );
	}
	
	public LoopException( String message, Throwable cause ) {
		super( message, cause );
	}
	
	public LoopException( Throwable cause ) {
		super( cause );
	}
	
}
