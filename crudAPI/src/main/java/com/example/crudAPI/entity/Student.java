package com.example.crudAPI.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import lombok.Data;

@Entity
@Data
public class Student{
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int age;
    private String course;
}

