package com.akshay.urlshortner.services;


import com.akshay.urlshortner.dto.JwtResponse;
import com.akshay.urlshortner.dto.LoginRequest;
import com.akshay.urlshortner.entity.User;
import com.akshay.urlshortner.repository.UserRepository;
import com.akshay.urlshortner.security.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;




@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.authenticationManager =authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    public User registerUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public JwtResponse authenticateUser(LoginRequest loginRequest){

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String jwt = jwtUtils.generateToken(userDetails);
            System.err.println("request is authenticated in userservice");
            return  new JwtResponse(jwt);
        } catch (BadCredentialsException ex) {
            throw new RuntimeException("Invalid username or password");
        }
    }

    public User findByUsername(String name) {
        return userRepository.findByUsername(name).orElseThrow(
                ()->new RuntimeException("User does not Exist with Username + name")
        );
    }
}

