package com.example.myblogapplication.services;
import com.example.myblogapplication.entities.User;
import com.example.myblogapplication.payloads.UserDto;
import java.util.List;

public interface UserService {
    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user,Integer Id);
    UserDto getUserById(Integer Id);
    List<UserDto> getAllUsers();
    void deleteUser(Integer Id);
}
