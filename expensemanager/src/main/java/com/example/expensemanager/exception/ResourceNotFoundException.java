package com.example.expensemanager.exception;



public class ResourceNotFoundException extends  RuntimeException{

    String message;
   public ResourceNotFoundException(String message){
        super(message);
        this.message = message;
   }
}
