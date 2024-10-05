package com.example.myblogapplication.payloads;

import com.example.myblogapplication.entities.User;
import lombok.Data;

@Data
public class JwtAuthResponse {

    private String token;
    private UserDto user;
}
