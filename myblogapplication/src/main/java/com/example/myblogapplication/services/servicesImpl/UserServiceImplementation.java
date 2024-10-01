package com.example.myblogapplication.services.servicesImpl;
import com.example.myblogapplication.entities.User;
import com.example.myblogapplication.exceptions.ResourceNotFoundException;
import com.example.myblogapplication.payloads.UserDto;
import com.example.myblogapplication.repositories.UserRepository;
import com.example.myblogapplication.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImplementation implements UserService {

    private UserRepository userRepository;
    @Autowired
    private ModelMapper modalMapper;

    @Autowired
    UserServiceImplementation (UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(UserDto userdto) {
        User user = this.dtoToUser(userdto);
         User savedUser  =  this.userRepository.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
       User user =  this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","Id",userId));
       user.setName(userDto.getName());
       user.setEmail(userDto.getEmail());
       user.setPassword(userDto.getPassword());
       user.setAbout(userDto.getAbout());
       User updatedUser = this.userRepository.save(user);
        return this.userToDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepository.findAll();
        List<UserDto> usersDto = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
        return usersDto;
    }

    @Override
    public void deleteUser(Integer userId) {
      User user =  this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
     this.userRepository.delete(user);
    }

    private  User dtoToUser(UserDto userdto){
        User user = this.modalMapper.map(userdto,User.class) ;
        return user;
    }

    private  UserDto userToDto (User user){
        UserDto userdto = this.modalMapper.map(user,UserDto.class);
        return userdto;
    }
}
