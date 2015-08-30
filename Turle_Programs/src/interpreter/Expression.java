package interpreter;

import visitor.*;

public interface Expression {
	
	public void interpret( Context commands );
	
	public void accept( Visitor aGuess );
	
	public void undo();
	
	public void redo();
	
	public String toString();
}
