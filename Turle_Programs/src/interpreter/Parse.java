package interpreter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parse {
	// store parsing commands from text file
	private List<String> commandContainer;
	// get instance of the Turtle

	public Parse( String fileName ) throws IOException {		
		this.commandContainer = new ArrayList<String>();
		
		File in = new File( fileName );
		FileReader readCommands = new FileReader( in );
		BufferedReader bufferCommands = 
				new BufferedReader( readCommands );
		
		String line;
		// read each line
		while ( ( line = bufferCommands.readLine() ) != null ) {
			// take out empty space or white space			
			line = line.trim();
			
			// skip empty line 
			if( line.isEmpty() ) continue;
			
			// split up each String when see an white space
			for( String command : line.split("\\s+") )
				// add to each command to the command container
				commandContainer.add( command );
		}
		// close file
		bufferCommands.close();
	}
	
	public List<String> getCommandContainer() {
		return commandContainer;
	}
}
