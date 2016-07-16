package dave.LitleHelper.mail;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.sun.mail.imap.protocol.FLAGS;

import dave.LitleHelper.Settings;
import dave.LitleHelper.Settings.PropertyValues;
import dave.LitleHelper.exception.LittleException;
import dave.LitleHelper.exception.LittleException.Err;

public class MailSender {

	private static final Settings SETTINGS = Settings.getInstance();

	public static void send(String[] to, String subject, String messageText) {
		send(to, subject, messageText, null);
	}

	public static void send(String[] to, String subject, String messageText, File file) {
		// create some properties and get the default Session
		Properties props = new Properties();
		props.put("mail.smtp.host", SETTINGS.getValue(PropertyValues.EMAIL_SMTP));
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.imap.starttls.enable", "true");
		props.put("mail.smtp.ssl.trust", SETTINGS.getValue(PropertyValues.EMAIL_SMTP));
		props.put("mail.smtp.port", SETTINGS.getValue(PropertyValues.EMAIL_SMTP_PORT));

		Session session = Session.getInstance(props, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(SETTINGS.getValue(PropertyValues.EMAIL_LOGIN),
						SETTINGS.getValue(PropertyValues.EMAIL_PASS));
			}
		});

		// session.setDebug(true);

		try {
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(SETTINGS.getValue(PropertyValues.EMAIL)));

			InternetAddress[] addresses = new InternetAddress[to.length];
			for (int i = 0; i < to.length; i++) {
				addresses[i] = new InternetAddress(to[i].trim());
			}

			msg.setRecipients(Message.RecipientType.TO, addresses);
			msg.setSubject(subject);
			msg.setSentDate(new Date());
			msg.setText(messageText);

			if (file != null) {
				MimeBodyPart messageBodyPart = new MimeBodyPart();

				Multipart multipart = new MimeMultipart();

				DataSource source = new FileDataSource(file);
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(file.getName());
				multipart.addBodyPart(messageBodyPart);

				msg.setContent(multipart);
			}

			Transport.send(msg);

			Store store = session.getStore("imap");
			store.connect(SETTINGS.getValue(PropertyValues.EMAIL_IMAP), SETTINGS.getValue(PropertyValues.EMAIL_LOGIN),
					SETTINGS.getValue(PropertyValues.EMAIL_PASS));

			Folder folder = (Folder) store.getFolder("Sent");
			if (!folder.exists()) {
				folder.create(Folder.HOLDS_MESSAGES);
			}

			folder.open(Folder.READ_WRITE);

			try {
				folder.appendMessages(new Message[] { msg });
				msg.setFlag(FLAGS.Flag.RECENT, true);
			} catch (Exception e) {
				throw new LittleException(Err.MAIL_SAVE_FAILURE, e);
			} finally {
				store.close();
			}

		} catch (MessagingException e) {
			throw new LittleException(Err.MAIL_SEND_FAILURE, e);
		}
	}

}
