package com.akshay.urlshortner.dto;

import lombok.Data;

@Data
public class JwtResponse {
    private String username;

    private String token;

    public JwtResponse(){

    }

    public JwtResponse( String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
