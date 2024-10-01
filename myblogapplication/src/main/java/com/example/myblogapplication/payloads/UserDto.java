package com.example.myblogapplication.payloads;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private  int id;
    @NotEmpty
    @Size(min = 4,message = "Please enter valid name")
    private  String name;

    @Email(message = "Please enter valid Email")
    private String email;

    @NotEmpty
    @Size(min = 4,max = 10,message = "Password must be between 4 to 10 Characters")
    private String password;

    @NotEmpty
    private String about;

    private String message;
}
