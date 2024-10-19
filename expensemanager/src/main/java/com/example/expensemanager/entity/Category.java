package com.example.expensemanager.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Category {
    @Id
    private Integer categoryId;
    private String category;
    private String categoryDesc;
    private String imageUrl;

}
