package murach.util;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import java.io.IOException;

public class MailUtilGmail {

    public static void sendMail(String to, String from, String subject, String body, boolean bodyIsHTML) throws IOException {
        
        // Lấy API Key từ biến môi trường
        final String sendGridApiKey = System.getenv("SENDGRID_API_KEY");
//        final String sendGridApiKey = "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";
        if (sendGridApiKey == null || sendGridApiKey.isEmpty()) {
            System.out.println("ERROR: SENDGRID_API_KEY chưa được cấu hình!");
            throw new IOException("SENDGRID_API_KEY environment variable is not set.");
        }

        Email fromEmail = new Email(from);
        Email toEmail = new Email(to);
        Content content = new Content(bodyIsHTML ? "text/html" : "text/plain", body);
        
        Mail mail = new Mail(fromEmail, subject, toEmail, content);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();
        
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            
            Response response = sg.api(request);
            
            System.out.println("SendGrid Status Code: " + response.getStatusCode());
            
            // SendGrid trả về 2xx cho thành công
            if (response.getStatusCode() >= 300) {
                System.out.println("SendGrid Body: " + response.getBody());
                throw new IOException("Failed to send email via SendGrid. Status: " + response.getStatusCode() + " Body: " + response.getBody());
            }
            
        } catch (IOException ex) {
            System.out.println("Error sending email: " + ex.getMessage());
            throw ex;
        }
    }
}