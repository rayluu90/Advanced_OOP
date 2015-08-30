package visitor;

import interpreter.*;

public interface Visitor {
	
	public void visit( Move move );
	
	public void visit( Turn turn );
	
	public void visit( Repeat repeat );
	
	public void visit( PenUp penUp );
	
	public void visit( PenDown penDown );
	
	public void visit( Program program );
}
