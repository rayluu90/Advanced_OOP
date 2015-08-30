package observer;

import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class Email {
	private String host, server, message, subject,
				   port, username, password;
	private List<String> recipients;
		
	public void setMessage( String message ) {
		this.message = message;
	}
	
	public void setSubject( String subject ) {
		this.subject = subject;
	}
	
	public void setRecipient( List<String> recipients) {
		this.recipients = recipients;
	}
	
	public void setAuthentication( String username, String password ) {
		this.username = username;
		this.password = password;
	}

	public void setServer( String server ) {
		this.server = server;
	}
	
	public void setHost( String host ) {
		this.host = host;
	}
	
	public void setPort( String portNumber) {
		this.port = portNumber;
	}
	
	private void validateInput( String toValidate ) {
		if( toValidate == null )
			throw new IllegalArgumentException();
	}
	
	private void validatePort( String portNumber ) {
		if( Integer.parseInt( portNumber ) < 0 ) 
			// add new exception
			throw new IllegalArgumentException();
	}
	
	private void validateRecipient( List<String> recipients ) {
		// recipients list is null or nothing in there, 
		// throw an exception
		if( recipients == null || recipients.size() == 0 )
			throw new IllegalArgumentException();
		
		// if any recipient is null, throw an exception
		for( String recipient : recipients ) 
			if ( recipient == null )
				throw new IllegalArgumentException();
	}
			
	private Session configuredConnection( 
						String username, String password ) {
		final String defaultServer = "mail.smtp.host",
		      defaultPort = "587";
	
		// use default server if server is not set
		if( this.server == null ) this.server = defaultServer;
		// use default port if port number is not set 
		if( this.port == null ) this.port = defaultPort;
		
		Properties properties = System.getProperties();
	    properties.setProperty("mail.smtp.auth", "true");
	    properties.setProperty("mail.smtp.starttls.enable", "true");
	    properties.setProperty( this.server, this.host );
	    properties.setProperty("mail.smtp.port", this.port );

		// Try to access with email address and password
		Session session = 
				Session.getDefaultInstance( 
						properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication( username, password );
			}
		});
	    return session;
	}
	
	// send email message to all of the recipients in the list
	public void send() throws MessagingException {

        validateInput( this.username );
        validateInput( this.password );
		// host associate with user name and password
		// need to manually input
		validateInput( this.host );
	
		// get session using username, password, host, port
		Session currentSession = 
				configuredConnection( username, password );
		
        validatePort( this.port );

        // check if there is any recipients in the list
        validateRecipient( this.recipients );
        
		// message object
        MimeMessage message = new MimeMessage( currentSession );
         
        // list of recipients
        for( String aRecipient : this.recipients ) {
	      	InternetAddress recipientAddress = 
	      			new InternetAddress( aRecipient );
	      	
	       	message.addRecipient( 
	       			Message.RecipientType.TO, recipientAddress );
        }
	    // from sender
        message.setFrom( new InternetAddress( username ) );
	    // subject of the email
        message.setSubject( this.subject );
	    // message email
        message.setText( this.message );
	    // send message
	    Transport.send( message );
	}
}
