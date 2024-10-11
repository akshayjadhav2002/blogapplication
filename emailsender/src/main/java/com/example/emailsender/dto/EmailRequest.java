package com.example.emailsender.dto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {
    String to;
    String subject;
    String body;
}
