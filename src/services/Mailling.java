/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;


/**
 *
 * @author mega pc
 */
public class Mailling {
    
// from,to,host,sub,content;

public Mailling(){}

public void sendemail(String mail,int code)
{
Properties prop = new Properties();

prop.put ("mail.smtp.auth", "true");
prop.setProperty("mail.smtp.starttls.enable", "true");
prop.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
prop.put ("mail.smtp.host", "smtp.gmail.com");
prop.put ("mail.smtp.port", "587");
Session s = Session.getInstance(prop, new Authenticator() {
    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication("ayedinader78@gmail.com","cojibyjumtxktdnk");
    }
});
try {
Message message = new MimeMessage(s);
message.setFrom(new InternetAddress("naderpidev@gmail.com"));
message.setRecipients(
  Message.RecipientType.TO, InternetAddress.parse(mail));
message.setSubject("verification code");

String msg ="votre code est : "+code;

MimeBodyPart mimeBodyPart = new MimeBodyPart();
mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

Multipart multipart = new MimeMultipart();
multipart.addBodyPart(mimeBodyPart);

message.setContent(multipart);

Transport.send(message);
}catch (MessagingException ex) {
 System.err.println(ex.getMessage());
}
}



     


}