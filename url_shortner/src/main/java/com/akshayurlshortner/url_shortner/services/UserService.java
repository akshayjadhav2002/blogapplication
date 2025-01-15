package com.akshayurlshortner.url_shortner.services;

import com.akshayurlshortner.url_shortner.dto.LoginRequest;
import com.akshayurlshortner.url_shortner.entity.User;
import com.akshayurlshortner.url_shortner.repository.UserRepository;
import com.akshayurlshortner.url_shortner.security.JwtAuthenticationResponse;
import com.akshayurlshortner.url_shortner.security.JwtUtils;
import io.micrometer.common.util.internal.logging.Slf4JLoggerFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.slf4j.SLF4JLogger;
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

    public JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest){

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
            return new JwtAuthenticationResponse(jwt);
        } catch (BadCredentialsException ex) {
            throw new RuntimeException("Invalid username or password");
        }
    }
}

