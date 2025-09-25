package com.example.expensemanager.controller;

import com.example.expensemanager.dto.ApiResponse;
import com.example.expensemanager.dto.LoginRequest;
import com.example.expensemanager.dto.LoginResponse;
import com.example.expensemanager.dto.UserDTO;
import com.example.expensemanager.entity.User;
import com.example.expensemanager.services.serviceImpl.JwtService;
import com.example.expensemanager.services.serviceImpl.UserServiceImplementation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final JwtService jwtService;
    static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private PasswordEncoder passwordEncoder;

    private UserServiceImplementation userServiceImplementation;
    public AuthController(JwtService jwtService,UserServiceImplementation userService ,PasswordEncoder passwordEncoder1) {
        this.jwtService = jwtService;
        this.userServiceImplementation = userService;
        this.passwordEncoder = passwordEncoder1;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        User user = userServiceImplementation.findUserByUsername(username);
        String savedPassword = user.getPassword();

        if(!ObjectUtils.isEmpty(user) && passwordEncoder.matches(password,savedPassword)) {
            String token = jwtService.generateToken(username);
            LoginResponse response = new LoginResponse();
            response.setToken(token);
            response.setUsername(username);
            logger.info("User with user name : " + username + "longed in");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new ApiResponse("User Not Found",false),HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> createUser(@RequestBody UserDTO userDTO){
        Boolean isCreated =  this.userServiceImplementation.createUser(userDTO);
        if(isCreated) {
            logger.info("user created successfully");
            return new ResponseEntity(new ApiResponse("User saved successfully", isCreated), HttpStatus.CREATED);
        }
        else{
            logger.info("user failed to create");
            return new ResponseEntity<>(new ApiResponse("Failed to save user",isCreated),HttpStatus.BAD_REQUEST);
        }
    }
}
