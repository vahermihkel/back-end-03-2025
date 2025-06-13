package ee.mihkel.veebipood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    public void sendEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("to-email");
        message.setSubject("Tere");
        message.setText("Sisu");
        message.setFrom("hello@demomailtrap.co");
        javaMailSender.send(message);
    }
}
