package com.akshay.urlshortner.services;


import com.akshay.urlshortner.entity.User;
import com.akshay.urlshortner.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDeatilsServiceImpl implements UserDetailsService {

    UserRepository userRepository;

    public UserDeatilsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
                ()-> new UsernameNotFoundException("User Not Found with username "+ username)
        );
        return UserDetailsImpl.build(user);
    }
}
