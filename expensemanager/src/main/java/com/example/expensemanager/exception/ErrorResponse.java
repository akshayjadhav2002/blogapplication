package com.example.expensemanager.exception;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {
    HttpStatus httpStatus;
    String message;

     public ErrorResponse(HttpStatus httpStatus, String message){
        this.httpStatus = httpStatus;
        this.message = message;
    }

}
