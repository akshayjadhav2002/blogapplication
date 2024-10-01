package com.example.myblogapplication.controllers;
import com.example.myblogapplication.payloads.ApiResponse;
import com.example.myblogapplication.payloads.UserDto;
import com.example.myblogapplication.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    UserService userService;
    @Autowired
     UserController(UserService userService){
        this.userService = userService;
    }

    //createUser
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
            UserDto createUser = this.userService.createUser(userDto);
            return new ResponseEntity<>(createUser, HttpStatus.CREATED);
    }

    //updateUser
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto , @PathVariable("userId") Integer Id){
        UserDto updatedUserDto = this.userService.updateUser(userDto, Id);
        return ResponseEntity.ok(updatedUserDto);
    }

    //DeleteUser
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer Id){
        this.userService.deleteUser(Id);
        return new ResponseEntity(new ApiResponse("User deleted Successfully",true),HttpStatus.OK);
    }

    //getUsersList
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> usersList = this.userService.getAllUsers();
        return   ResponseEntity.ok(usersList);
    }
    //getSingleUserById
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser( @PathVariable() Integer userId){
        UserDto userDto = this.userService.getUserById(userId);
        return ResponseEntity.ok(userDto);
    }
}
