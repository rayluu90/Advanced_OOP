
import java.io.IOException;
import interpreter.*;
import visitor.*;

public class Execute {
	
	public void execute() throws IOException{	
		
		// parse the text file
		Parse file = new Parse( "complex.txt" );
		
		// create a context to hold the parsed commands from the file
		Context commands = new Context( file.getCommandContainer() );
		
		// put the context in the program and interpret the contents
		Program program = new Program(); 
		
		program.interpret( commands );
		
		CommandVisitor guessCommand = new CommandVisitor();
		
		program.accept( guessCommand );
		
		System.out.println( commands.getTurtleInstance().location().toString() );
		
//		System.out.println( program.toString() );
		
		System.out.println();
		
//		for( Expression command : guessCommand.getCommands() )
//			System.out.println( command );
		
		CommandVistorManager comand = new CommandVistorManager( guessCommand.getCommands(), true );
		
		comand.execute();
		comand.execute();
		comand.execute();
		comand.execute();

		
		System.out.println( commands.getTurtleInstance().location().toString() );
		
		for( Expression command : guessCommand.getCommands() )
			System.out.println( command );

		
		
		DistanceVisitor guess = new DistanceVisitor();
		
		program.accept( guess );
		
		System.out.println( );

		
		System.out.println("Total distance Turtle travel: " + guess.getDistance() );
		
//		Expression command = Program.hosts.get(Program.hosts.size() - 1);
//		
//		command.undo();
//		
//		System.out.println( Turtle.getInstance().location().toString() );

		
	}
	
	public static void main( String[]a ) {
		try { 
			Execute commands = new Execute();
			commands.execute();
		} catch ( IOException e) {
			e.printStackTrace();
		}
	}
}

