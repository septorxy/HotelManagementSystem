package Resources;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

//Code inspired from https://www.youtube.com/watch?v=A7HAB5whD6I

public class SendEmail {

    public static void sendMailEmp(String recipient, String login, String password) throws MessagingException {
        System.out.println("Preparing...");
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");


        String myAcc = "hotelWPUT@gmail.com";
        String pass = "tr1v14l 45f";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAcc, pass);
            }
        });


        Message message = prepareMessageEmp(session, myAcc, recipient, login, password);

        assert message != null;
        Transport.send(message);

        System.out.println("Sent");
    }

    private static Message prepareMessageEmp(Session session, String myAcc, String recip, String login, String pass){
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(myAcc));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recip));
            message.setSubject("Employee Login Details");
            message.setText("Hi!\nYour login details are as follows:\nUsername: " + login + "\nPassword: " + pass + "\nWelcome to the team!\nSincerely,\nThe management");
            return message;
        } catch (MessagingException e) {
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public static void sendMailCustom(String recipient, String data) throws MessagingException {
        System.out.println("Preparing...");
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");


        String myAcc = "hotelWPUT@gmail.com";
        String pass = "tr1v14l 45f";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAcc, pass);
            }
        });


        Message message = prepareMessageCustom(session, myAcc, recipient, data);

        assert message != null;
        Transport.send(message);

        System.out.println("Sent");
    }

    private static Message prepareMessageCustom(Session session, String myAcc, String recip, String data) {
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(myAcc));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recip));
            message.setSubject("Mail from Maritim automated system");
            message.setText(data);
            return message;
        } catch (MessagingException e) {
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
}