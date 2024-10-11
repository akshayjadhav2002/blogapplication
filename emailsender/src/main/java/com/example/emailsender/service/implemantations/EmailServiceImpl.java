package com.example.emailsender.service.implemantations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;



@Service
public class EmailServiceImplementation {


    JavaMailSender javaMailSender;
    @Autowired
    EmailServiceImplementation(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }
    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);
        simpleMailMessage.setFrom(fromEmail);
        System.err.println("inside the implementation check error");
        javaMailSender.send(simpleMailMessage);
    }

//    @Override
//    public void sendEmail(String[] to, String subject, String body) {
//        SimpleMailMessage mail = new SimpleMailMessage();
//        mail.setTo(to);
//        mail.setSubject(subject);
//        mail.setText(body);
//        mail.setFrom("akshaybloger@gmail.com");
//        javaMailSender.send(mail);
//    }
////
//    @Override
//    public void sendEmailWithFile(String to, String subject, String body, InputStream file) {
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        try {
//            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
//            mimeMessageHelper.setTo(to);
//            mimeMessageHelper.setSubject(subject);
//            mimeMessageHelper.setText(body, true);
//            mimeMessageHelper.setFrom(fromEmail);
//
//            // Attach file from InputStream using ByteArrayResource
//            ByteArrayResource resource = new ByteArrayResource(file.readAllBytes());
//            mimeMessageHelper.addAttachment("filename.png", resource);
//
//            javaMailSender.send(mimeMessage);
//        } catch (MessagingException | IOException e) {
//            System.err.println("Failed to send email: " + e.getMessage());
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public void sendEmailWithFile(String[] to, String subject, String body, InputStream file) {
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        try {
//            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
//            mimeMessageHelper.setTo(to);
//            mimeMessageHelper.setSubject(subject);
//            mimeMessageHelper.setText(body, true);
//            mimeMessageHelper.setFrom(fromEmail);
//
//            // Attach file from InputStream using ByteArrayResource
//            ByteArrayResource resource = new ByteArrayResource(file.readAllBytes());
//            mimeMessageHelper.addAttachment("filename.png", resource);
//
//            javaMailSender.send(mimeMessage);
//        } catch (MessagingException | IOException e) {
//            System.err.println("Failed to send email: " + e.getMessage());
//            throw new RuntimeException(e);
//        }
//    }

}
