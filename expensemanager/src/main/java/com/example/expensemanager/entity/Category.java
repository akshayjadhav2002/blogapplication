package com.example.expensemanager.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    private Integer categoryId;
    private String category;
    private String categoryDesc;
    private String imageUrl;
    @OneToMany
    private List<Transaction> transactions = new ArrayList<>();

}
