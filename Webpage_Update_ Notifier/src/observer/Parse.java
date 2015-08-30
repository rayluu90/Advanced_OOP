package observer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import exceptions.ParsingException;

public class Parse {
	private List<String> fileContents;
	
	public Parse( String fileName) throws IOException {		
		this.fileContents = new ArrayList<String>();
		
		File in = new File( fileName );
		FileReader readSubjects = new FileReader( in );
		BufferedReader bufferSubjects = 
				new BufferedReader( readSubjects );
		
		String line;
		// read each line
		while ( ( line = bufferSubjects.readLine() ) != null ) {
			// take out empty space or white space			
			line = line.trim();
			
			// skip empty line 
			if( line.isEmpty() ) continue;
			
			this.fileContents.add( line );
		}
		// close file
		bufferSubjects.close();
	}
	
	// return subject with associated observers
	public Map<String,List<String>> getSubjects() {
		HashMap<String,List<String>> container = 
									new HashMap<String,List<String>>();
		
		// for each line in the file
		// break it up to create a subject and observers
		for( String eachLine : this.fileContents ) {
			// Temporarily list to hold content of each line 
			List<String> currentLineContents = new LinkedList<String>();
			
			// spit up the subject and observer of each line 
			for( String content : eachLine.split("\\s+") )
				currentLineContents.add( content );
			
			// validate the current line
			validate( currentLineContents );
			
			// get the URL
			String URL = currentLineContents.get( 0 );
			// remove URL from the list
			// what left will be observers 
			currentLineContents.remove( 0 );
			
			// if URL is not in the list, add it
			if( !container.containsKey(URL) )
			// use URL as a key to store and retrieve list of observers
				container.put( URL, currentLineContents );
		}
		return container;
	}
		
	private void validate( List<String> contents ) {
		int size = contents.size();
		String URLRegex = "^(https?|ftp|file)://" + 
						  "[-a-zA-Z0-9+&@#/%?=~_|!:,.;]" + 
						  "*[-a-zA-Z0-9+&@#/%=~_|]";
		
		// if there is no observer, throw an exception
		if( size == 1 )
			throw new ParsingException( "Missing Observer(s)!" );
		
		// if the URL doesn't match the regular expression of URL
		// throw an exception
		if( !contents.get( 0 ).matches( URLRegex ) )
			throw new ParsingException( "Not a web URL!" );
		
		// if multiple URL on the same line, invalid entry
		for( int position = 1; position < size; position++ )
			if( contents.get( position ).matches( URLRegex ) )
				throw new ParsingException(
						"Only one web URL allow per line");
	}
}
