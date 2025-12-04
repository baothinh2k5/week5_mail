package murach.util;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;

public class MailUtilGmail {

    public static void sendMail(String to, String from,
            String subject, String body, boolean bodyIsHTML)
            throws MessagingException {

        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", "smtp.sendgrid.net");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // ========================================================
                // DÁN KEY CỦA BẠN VÀO GIỮA 2 DẤU NGOẶC KÉP Ở DƯỚI
                // ========================================================
                String apiKey = "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"; 
                
                return new PasswordAuthentication("apikey", apiKey);
            }
        };

        Session session = Session.getInstance(props, authenticator);
        
        // Bỏ comment dòng dưới nếu muốn xem log chi tiết khi gửi lỗi
        session.setDebug(true); 

        Message message = new MimeMessage(session);
        message.setSubject(subject);
        
        if (bodyIsHTML) {
            message.setContent(body, "text/html; charset=UTF-8");
        } else {
            message.setText(body);
        }

        Address fromAddress = new InternetAddress(from);
        Address toAddress = new InternetAddress(to);
        message.setFrom(fromAddress);
        message.setRecipient(Message.RecipientType.TO, toAddress);

        Transport.send(message);
    }
}