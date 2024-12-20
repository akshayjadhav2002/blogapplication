//package com.example.myblogapplication.security;
//
//import com.example.myblogapplication.entities.User;
//import com.example.myblogapplication.exceptions.ResourceNotFoundException;
//import com.example.myblogapplication.repositories.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    @Autowired
//     private  UserRepository userRepository;
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//      User user =  this.userRepository.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User","userName"+ username,0));
//        return user;
//    }
//}
