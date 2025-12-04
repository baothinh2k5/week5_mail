package murach.util;

import java.util.Properties;
import jakarta.mail.*;             // Đã đổi từ javax
import jakarta.mail.internet.*;    // Đã đổi từ javax

public class MailUtilGmail {

    public static void sendMail(String to, String from,
            String subject, String body, boolean bodyIsHTML)
            throws MessagingException {

        // 1 - Cấu hình pha (Session)
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtps.host", "smtp.gmail.com");
        props.put("mail.smtps.port", "465");
        props.put("mail.smtps.auth", "true");
        props.put("mail.smtps.quitwait", "false");
        
        Session session = Session.getDefaultInstance(props);
        session.setDebug(true); // Bật log để xem quá trình gửi

        // 2 - Tạo tin nhắn (Message)
        Message message = new MimeMessage(session);
        message.setSubject(subject);
        
        if (bodyIsHTML) {
            // Quan trọng: Thêm charset=UTF-8 để hiển thị tiếng Việt
            message.setContent(body, "text/html; charset=UTF-8");
        } else {
            message.setText(body);
        }

        // 3 - Thiết lập địa chỉ
        // Lưu ý: Gmail thường sẽ tự động ghi đè "From" bằng email đăng nhập thực tế
        Address fromAddress = new InternetAddress(from);
        Address toAddress = new InternetAddress(to);
        message.setFrom(fromAddress);
        message.setRecipient(Message.RecipientType.TO, toAddress);

        // 4 - Kết nối và gửi (Transport)
        Transport transport = session.getTransport();
        
        // --- PHẦN QUAN TRỌNG NHẤT ---
        // Bạn phải thay đổi 2 dòng dưới đây bằng thông tin thật của bạn
        String myEmail = "coixoaygio1999@gmail.com"; 
        String myAppPassword = "tvxs gyoa cbhk yapq"; // KHÔNG PHẢI MẬT KHẨU ĐĂNG NHẬP
        
        transport.connect(myEmail, myAppPassword);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
}