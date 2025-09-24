package com.example.expensemanager.services.serviceImpl;

import com.example.expensemanager.dto.ApiResponse;
import com.example.expensemanager.dto.UserDTO;
import com.example.expensemanager.entity.User;
import com.example.expensemanager.repository.UserRepository;
import com.example.expensemanager.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class UserServiceImplementation implements UserService {

    private final ModelMapper modelMapper;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    final static Logger logger = LoggerFactory.getLogger(UserServiceImplementation.class);
    private UserServiceImplementation (UserRepository userRepository, ModelMapper modelMapper,PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Boolean createUser(UserDTO userDTO) {
        if(!ObjectUtils.isEmpty(userDTO)){
            User user = modelMapper.map(userDTO,User.class);
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            User savedUser= userRepository.save(user);
            logger.info("User saved in database - {}",savedUser);
            return true;
        }
        else{
            logger.info("fail to save the User in Database");
            return false;
        }
    }

    @Override
    public Object updateUser(UserDTO userDTO) {
        if (!ObjectUtils.isEmpty(userDTO)){
                User savedUser = userRepository.getReferenceById(userDTO.getUserId());
                savedUser.setUserName(userDTO.getUserName());
                savedUser.setEmail(userDTO.getEmail());
                savedUser.setPassword(userDTO.getPassword());
                savedUser.setSubscribeToMail(userDTO.isSubscribeToMail());
                logger.info("User update in database successfully - {}", savedUser);
                return userRepository.save(savedUser);


        }
        else {
            logger.info("Transaction update failed");
            return new ApiResponse( userDTO.getUserName() + " not Found",false);
        }
    }

    @Override
    public Boolean deleteUser(Integer userId) {
        User user = userRepository.getReferenceById(userId);
        if(!ObjectUtils.isEmpty(user)){
            try{
            userRepository.delete(user);
            logger.info("User deleted successfully from Database - {}",user);
            return  true;
            }
            catch (Exception exception){
                logger.error(exception.getMessage());
                return false;
            }
        }
        else{
            logger.info("User failed to delete");
            return false;
        }
    }

    @Override
    public List<User> getAllUser() {
        List<User> userList = userRepository.findAll();
        logger.info("All user list fetched from database");
        return userList;
    }

    @Override
    public Object getUserById(Integer userId) {
        if(userId<0){
            logger.info("Invalid user id received");
            return new ApiResponse("Invalid userId",false);
        }
        else{
            User user =userRepository.getReferenceById(userId);
            logger.info("User fetched from Database successfully - {}",user);
            return user;
        }
    }
    public User findUserByUsername(String username) {
        return userRepository.findFirstByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}
