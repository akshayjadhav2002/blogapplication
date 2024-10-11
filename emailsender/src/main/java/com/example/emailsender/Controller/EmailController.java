package com.example.emailsender.Controller;

import com.example.emailsender.dto.EmailRequest;
import com.example.emailsender.dto.EmailResponse;
import com.example.emailsender.service.implemantations.EmailService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/email")
public class EmailController {

    private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

    private  EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {

        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<EmailResponse> sendEmail(@Valid @RequestBody EmailRequest emailRequest) throws Exception {
        logger.info("Received email request: {}", emailRequest);

        emailService.sendMail(emailRequest);

        logger.info("Email sent successfully to {}", emailRequest.getTo());

        return new ResponseEntity<>(new EmailResponse(emailRequest.getTo(), "Mail sent successfully", true),
                HttpStatus.OK);
    }

    //
//    @PostMapping("/send-with-file")
//    public ResponseEntity<EmailResponse> sendWithFile(@RequestPart EmailRequest emailRequest , @RequestPart MultipartFile file) throws IOException {
//            emailService.sendEmailWithFile(emailRequest.getTo(),emailRequest.getSubject(),emailRequest.getBody(),file.getInputStream());
//        return new ResponseEntity<>(new EmailResponse(emailRequest.getTo(),"Mail sent successfully", true),HttpStatus.OK);
//    }
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        logger.info("Test endpoint hit.");
        return new ResponseEntity<>("Service running OK", HttpStatus.OK);
    }
}
