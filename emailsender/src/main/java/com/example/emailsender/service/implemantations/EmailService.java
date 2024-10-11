package com.example.emailsender.service.implemantations;

import com.example.emailsender.dto.EmailRequest;

public interface EmailService {
    Object sendMail(EmailRequest emailRequest);
}
