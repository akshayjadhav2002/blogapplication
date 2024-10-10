//package com.example.myblogapplication.controllers;
//
//import com.example.myblogapplication.exceptions.ApiException;
//import com.example.myblogapplication.payloads.JwtAuthRequest;
//import com.example.myblogapplication.payloads.JwtAuthResponse;
//import com.example.myblogapplication.payloads.UserDto;
//import com.example.myblogapplication.repositories.UserRepository;
//import com.example.myblogapplication.security.JwtTokenHelper;
//import com.example.myblogapplication.services.UserService;
//import jakarta.validation.Valid;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/v1/auth/")
//public class AuthController {
//
//    @Autowired
//    private JwtTokenHelper jwtTokenHelper;
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private UserRepository userRepo;
//
//    @Autowired
//    private ModelMapper mapper;
//
//    @PostMapping("/login")
//    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
//        // Authenticate the user
//        this.authenticate(request.getUsername(), request.getPassword());
//
//        // Load user details
//        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
//
//        // Generate JWT token
//        String token = this.jwtTokenHelper.generateToken(userDetails);
//
//        // Create response
//        JwtAuthResponse response = new JwtAuthResponse();
//        response.setToken(token);
//        response.setUser(this.mapper.map(userRepo.findByEmail(request.getUsername()), UserDto.class));
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//    private void authenticate(String username, String password) throws Exception {
//        try {
//            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
//            this.authenticationManager.authenticate(authenticationToken);
//        } catch (BadCredentialsException e) {
//            // Handle invalid credentials
//            throw new ApiException("Invalid username or password", HttpStatus.UNAUTHORIZED);
//        }
//    }
//
//    // register new user api
//
//    @PostMapping("/register")
//    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto) {
//        UserDto registeredUser = this.userService.registerNewUser(userDto);
//        return new ResponseEntity<UserDto>(registeredUser, HttpStatus.CREATED);
//    }
//
//}
