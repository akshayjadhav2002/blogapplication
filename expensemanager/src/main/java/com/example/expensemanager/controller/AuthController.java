package com.example.expensemanager.controller;

import com.example.expensemanager.dto.ApiResponse;
import com.example.expensemanager.dto.LoginRequest;
import com.example.expensemanager.dto.LoginResponse;
import com.example.expensemanager.entity.User;
import com.example.expensemanager.services.serviceImpl.JwtService;
import com.example.expensemanager.services.serviceImpl.UserServiceImplementation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final JwtService jwtService;

    private UserServiceImplementation userServiceImplementation;
    public AuthController(JwtService jwtService,UserServiceImplementation userService) {
        this.jwtService = jwtService;
        this.userServiceImplementation = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        User user = userServiceImplementation.findUserByUsername(username);
        if(!ObjectUtils.isEmpty(user)) {
            String token = jwtService.generateToken(username);
            LoginResponse response = new LoginResponse();
            response.setToken(token);
            response.setUsername(username);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new ApiResponse("User Not Found",false),HttpStatus.OK);
        }
    }
}
