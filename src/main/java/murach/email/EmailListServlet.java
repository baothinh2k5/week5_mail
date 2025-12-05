package murach.email;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import murach.business.User;
import murach.data.UserDB;
import murach.util.MailUtilGmail;

@WebServlet(urlPatterns = {"/emailList"})
public class EmailListServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy hành động hiện tại
        String action = request.getParameter("action");
        if (action == null) {
            action = "join";  // hành động mặc định
        }

        String url = "/index.jsp";
        
        if (action.equals("join")) {
            url = "/index.jsp";    // Trang tham gia
        } 
        else if (action.equals("add")) {
            // Lấy tham số từ request
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");

            // Lưu dữ liệu vào đối tượng User
            User user = new User(firstName, lastName, email);
            
            // Giả lập lưu vào DB (Cần class UserDB)
            // UserDB.insert(user);
            
            request.setAttribute("user", user);

            // Cấu hình gửi mail
            String to = email;
            // LƯU Ý: Email 'from' này PHẢI được xác thực trong SendGrid (Sender Identity)
            String from = "coixoaygio1999@gmail.com"; 
            String subject = "Welcome to our email list";
            String body = "Dear " + firstName + ",\n\n" +
                "Thanks for joining our email list. We'll make sure to send " +
                "you announcements about new products and promotions.\n" +
                "Have a great day and thanks again!\n\n" +
                "Kelly Slivkoff\n" +
                "Mike Murach & Associates";
            
            // SendGrid hỗ trợ cả HTML và Text, ở đây ta để false (text thuần)
            boolean isBodyHTML = false;

            try {
                // Gọi hàm gửi mail qua SendGrid API
                MailUtilGmail.sendMail(to, from, subject, body, isBodyHTML);
                
                // Gửi thành công -> chuyển sang trang cảm ơn
                url = "/thanks.jsp";
                
            } catch (IOException e) {
                // BẮT LỖI IOException (Thay vì MessagingException cũ)
                String errorMessage 
                        = "ERROR: Unable to send email via SendGrid. <br>"
                        + "Check Tomcat logs (console) for details.<br>"
                        + "ERROR MESSAGE: " + e.getMessage();
                
                request.setAttribute("errorMessage", errorMessage);
                
                // Ghi log lỗi vào server log
                this.log(
                        "Unable to send email via SendGrid. \n"
                        + "Details: \n"
                        + "TO: " + email + "\n"
                        + "FROM: " + from + "\n"
                        + "EXCEPTION: " + e.getMessage());
                
                // Vẫn chuyển sang trang thanks (hoặc trang error tùy logic của bạn)
                // Thông thường nếu lỗi thì vẫn báo là đã đăng ký nhưng mail bị lỗi
                url = "/thanks.jsp"; 
            }
        }
        
        // Chuyển hướng request/response
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}