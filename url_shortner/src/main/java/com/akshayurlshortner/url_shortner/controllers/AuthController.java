package com.akshayurlshortner.url_shortner.controllers;

import com.akshayurlshortner.url_shortner.dto.LoginRequest;
import com.akshayurlshortner.url_shortner.dto.RegisterRequest;
import com.akshayurlshortner.url_shortner.entity.User;
import com.akshayurlshortner.url_shortner.security.JwtAuthenticationResponse;
import com.akshayurlshortner.url_shortner.services.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {


    UserService userService;

    public  AuthController (UserService userService){
        this.userService = userService;
    }

    @PostMapping("/public/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        userService.registerUser(user);
        return ResponseEntity.ok("User Registered Successfully");
    }

    @PostMapping(value = "/public/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtAuthenticationResponse> loginUser( @RequestBody LoginRequest loginRequest) {
        JwtAuthenticationResponse response = userService.authenticateUser(loginRequest);
        return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
    }
}
