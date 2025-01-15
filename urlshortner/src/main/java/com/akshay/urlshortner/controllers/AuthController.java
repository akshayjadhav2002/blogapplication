package com.akshay.urlshortner.controllers;



import com.akshay.urlshortner.dto.JwtResponse;
import com.akshay.urlshortner.dto.LoginRequest;
import com.akshay.urlshortner.dto.RegisterRequest;
import com.akshay.urlshortner.entity.User;
import com.akshay.urlshortner.services.UserService;
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
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
           JwtResponse response = userService.authenticateUser(loginRequest);
           response.setUsername(loginRequest.getUsername());
            return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Failed to Login In",HttpStatus.BAD_REQUEST);
        }

    }
}
