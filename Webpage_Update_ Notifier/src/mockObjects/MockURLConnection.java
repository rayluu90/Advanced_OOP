package mockObjects;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class MockURLConnection extends URLConnection  {
	private URL original; 
	private long desiredTime;
	
	public MockURLConnection( URL original, long desiredTimeChange ) 
			throws IOException {
		super( original );
		this.original = original;
		this.desiredTime = desiredTimeChange;
	}

	public void connect() {}
	
	public long getLastModified() {
		URLConnection orignalURLConnection = null;
		
		try {
			// get the original ULR connection
			orignalURLConnection = this.original.openConnection();
		} catch (IOException e) {
			System.exit(0);
			e.printStackTrace();
		}
		// fake the modified time by changing the time
		long fakeModifiedTime = 
				orignalURLConnection.getLastModified() + this.desiredTime;
		
		return fakeModifiedTime;
	}
}
