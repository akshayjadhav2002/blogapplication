package com.example.emailsender.service.implemantations;

import com.example.emailsender.dto.EmailRequest;
import com.example.emailsender.dto.EmailResponse;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String fromEmail;
    @Override
    public Object sendMail(EmailRequest emailRequest) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom("unkknown@gmail.com");
            mimeMessageHelper.setTo(emailRequest.getTo());
            mimeMessageHelper.setSubject(emailRequest.getSubject());
            mimeMessageHelper.setText(emailRequest.getBody());

            javaMailSender.send(mimeMessage);

            log.info("Message Sent Successfully to: {}", emailRequest.getTo());
            return new EmailResponse(emailRequest.getTo(),"Email Sent Successfully",true);
        }
        catch (Exception e) {
            log.error("sendEmail() | Error : {}", e.getMessage());
            throw new RuntimeException(e);
        }


    }


}