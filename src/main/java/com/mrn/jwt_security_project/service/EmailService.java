package com.mrn.jwt_security_project.service;

import com.sun.mail.smtp.SMTPTransport;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

import static com.mrn.jwt_security_project.constant.EmailConstant.*;

@Service
public class EmailService {

    private static final String HELLO = "Hello ";
    private static final String YOUR_NEW_ACCOUNT_PASSWORD_IS = ", \n \n Your new account password is: ";
    private static final String THE_SUPPORT_TEAM = "\n \n The support Team.";

    private Session getEmailSession() {
        Properties properties = System.getProperties();
        properties.put(SMTP_HOST, GMAIL_SMTP_SERVER);
        properties.put(SMTP_AUTH, true);
        properties.put(SMTP_PORT, DEFAULT_PORT);
        properties.put(SMTP_STARTTLS_ENABLE, true);
        properties.put(SMTP_STARTTLS_REQUIRED, true);

        return Session.getInstance(properties, null);
    }

    private Message createEmail(String firstName, String password, String email) {
        Message message = new MimeMessage(getEmailSession());
        try {
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email, false));
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(CC_EMAIL, false));
            message.setSubject(EMAIL_SUBJECT);
            message.setText(HELLO + firstName + YOUR_NEW_ACCOUNT_PASSWORD_IS + password + THE_SUPPORT_TEAM);
            message.setSentDate(new Date());
            message.saveChanges();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return message;
    }


    public void sendNewPasswordEmail(String firstName, String password, String email) {
        Message message = createEmail(firstName, password, email);
        try {
            SMTPTransport smtpTransport = (SMTPTransport) getEmailSession().getTransport(SIMPLE_EMAIL_TRANSFER_PROTOCOL);
            smtpTransport.connect(GMAIL_SMTP_SERVER, USERNAME, PASSWORD);
            smtpTransport.sendMessage(message, message.getAllRecipients());
            smtpTransport.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

}
