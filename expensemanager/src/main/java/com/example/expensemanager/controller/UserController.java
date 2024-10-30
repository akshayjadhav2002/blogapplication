package com.example.expensemanager.controller;

import com.example.expensemanager.dto.ApiResponse;
import com.example.expensemanager.dto.UserDTO;
import com.example.expensemanager.entity.User;
import com.example.expensemanager.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private UserService userService;

    //logging slf4j object
    static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController ( UserService userService){
        this.userService = userService;
    }


    @PostMapping("/createUser/")
    public ResponseEntity<Object> createUser(@RequestBody UserDTO userDTO){
        Boolean isCreated =  this.userService.createUser(userDTO);
        if(isCreated) {
            logger.info("user created successfully");
            return new ResponseEntity(new ApiResponse("User saved successfully", isCreated), HttpStatus.CREATED);
        }
        else{
            logger.info("user failed to create");
            return new ResponseEntity<>(new ApiResponse("Failed to save user",isCreated),HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUserById(@PathVariable Integer userId) {
        Object user = this.userService.getUserById(userId);
        if (!ObjectUtils.isEmpty(user)){
            logger.info("user fetched successfully");
            return new ResponseEntity<Object>(user, HttpStatus.OK);
        }
        else{
            logger.info("user failed to fetch");
            return new ResponseEntity<>(new ApiResponse("failed to fetch User",false),HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/")
    public ResponseEntity<Object> getUser() {
        List<User> userList = this.userService.getAllUser();
        if (!ObjectUtils.isEmpty(userList)){
            logger.info("user list fetched  successfully");
            return new ResponseEntity<>(userList, HttpStatus.OK);
        }
        else{
            logger.info("user list failed to fetched");
            return new ResponseEntity<>(new ApiResponse("Fail to get all list",false),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("update/")
    public ResponseEntity<Object> updateUser(@RequestBody UserDTO userDTO){
        try {
            Object savedUser = userService.updateUser(userDTO);
            logger.info("User updated successfully");
            return new ResponseEntity(savedUser, HttpStatus.ACCEPTED);
        }
        catch (Exception exception){
            logger.warn(exception.getMessage());
            return new ResponseEntity<>( new ApiResponse("Failed to Update User",false),HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("delete/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId){
        Boolean isDeleted = userService.deleteUser(userId);
        if(isDeleted){
            logger.info("User deleted successfully");
            return new ResponseEntity<>(new ApiResponse("User deleted Successfully",isDeleted),HttpStatus.ACCEPTED);
        }
        else{
            logger.info("User failed to delete");
            return new ResponseEntity<>(new ApiResponse("User failed to deleted",isDeleted),HttpStatus.NOT_FOUND);
        }
    }

}
