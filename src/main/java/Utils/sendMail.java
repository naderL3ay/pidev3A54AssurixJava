package Utils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;
import javax.mail.PasswordAuthentication;

public class sendMail {
    public static void send() throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.protokls", "TLSv1.2");
        props.put("mail.smtp.host", "smtp.mailtrap.io");
        props.put("mait.smtp.port", "2525");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("67bf386c445c7b", "197c7a190fdb45");
            }
        });
        String directoryPath = "./src/main/java/Utils"; // Current directory

        try {
            // Get the directory path as a Path object
            Path dir = Paths.get(directoryPath);

            // Get a stream of all the files in the directory
            Files.list(dir)
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String htmlFilePath = "./src/main/java/Utils/emailTemplate.html";
        try {
            String htmlContent = new String(Files.readAllBytes(Paths.get(htmlFilePath)));

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("Contact@esprit.com"));
            //    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("bekir.emna@esprit.tn"));
            message.setSubject("Une nouvelle réclamation à été ajoutée");
            // message.setText("Hello, this is a test email from JavaFX.");
            message.setContent(htmlContent, "text/html");
            Transport.send(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
