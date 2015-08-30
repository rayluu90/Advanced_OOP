package observer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.mail.MessagingException;

@SuppressWarnings("serial")
public class Mail implements Observer, Serializable {
	private boolean hasEmailSend;
	
	public Mail( Observable subject ) {
		subject.addObserver( this );
	}

	public void update( Observable subject, Object message ) {
		String emailSubject = "Update Notification",
		       emailMessage = "URL '" + subject.toString() + 
		       				  "' has modified its contents at " + 
		       				  message.toString();
		
		// usually, the list of recipients would obtain from a database
		List<String> recipients = new ArrayList<String>();
		recipients.add( "twin.performance15@gmail.com" );
		recipients.add( "loiluu90@gmail.com" );
		
		final String username = "username@gmail.com",
				     password = "password",
				     host = "smtp.gmail.com";
		
		Email aMail = new Email();
		
		// to recipient
		aMail.setRecipient( recipients );
		// subject of the email
		aMail.setSubject( emailSubject );
		// message content
		aMail.setMessage( emailMessage );
		aMail.setHost( host );
		aMail.setAuthentication( username, password );
		
		try {
			aMail.send();
		} catch (MessagingException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		this.hasEmailSend = true;
	}
	
	public boolean isSendSuccess() {
		return this.hasEmailSend;
	}
	
	public String toString() {
		return "mail";
	}
}
