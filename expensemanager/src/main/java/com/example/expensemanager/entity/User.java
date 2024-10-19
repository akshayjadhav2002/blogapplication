package com.example.expensemanager.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jdk.jfr.Enabled;

@Entity
public class User {
    @Id
    private Integer userId;
    private String user;
    private String userName;
    private String password;

}
