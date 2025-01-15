package com.akshayurlshortner.url_shortner.security;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private String token;

    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }
}
