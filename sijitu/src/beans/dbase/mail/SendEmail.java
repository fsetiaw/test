package beans.dbase.mail;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 * Session Bean implementation class SendEmail
 */
@Stateless
@LocalBean
public class SendEmail {

    /**
     * Default constructor. 
     */
    public SendEmail() {
        // TODO Auto-generated constructor stub
    }

    
    public static void senEmail(String target_email, String subjek, String msg) {
    	final String username = "info@cg2net.id";
		final String password = "findingCg2";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("from-email@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(target_email));
			message.setSubject(subjek);
			message.setText(msg);

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
    }
}
