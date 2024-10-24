package com.example.expensemanager.services;

import com.example.expensemanager.dto.UserDTO;
import com.example.expensemanager.entity.User;
import java.util.List;

public interface UserService {
    Boolean createUser(UserDTO user);
    Object updateUser(UserDTO user);
    Boolean deleteUser(Integer UserId);
    List<User> getAllUser();
    Object getUserById(Integer userId);
}
