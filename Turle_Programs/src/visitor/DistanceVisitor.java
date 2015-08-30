package visitor;

import interpreter.*;

public class DistanceVisitor implements Visitor {
	
	private int distance;
	
	// each time see a move object,
	// get the distance and add it up 
	public void visit( Move move ) {
		distance += move.getDistance();
	}
	
	public int getDistance() {
		return distance;
	}
	
	public void visit( Turn turn ) {}
	
	public void visit( Repeat repeat ) {}
	
	public void visit( PenUp penUp ) {}
	
	public void visit( PenDown penDown ) {}
	
	public void visit( Program program ) {}
}
