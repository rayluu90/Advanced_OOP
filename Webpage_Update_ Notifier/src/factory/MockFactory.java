package factory;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import static org.mockito.Mockito.*;

@SuppressWarnings("serial")
public class MockFactory extends Factory {
	
	// produce mock URLConnection 	
	public URLConnection produceURLConnection( URL anURL ) 
			throws IOException {	
		
		return mock( URLConnection.class );
	}
}
