package com.example.myblogapplication.security;

import lombok.Data;

@Data
public class JwtAuthResponse {

    private String token;
    private  String userName;
}
