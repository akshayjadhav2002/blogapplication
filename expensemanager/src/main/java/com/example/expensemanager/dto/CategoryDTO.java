package com.example.expensemanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class CategoryDTO {
    private Integer categoryId;
    private String category;
    private String categoryDesc;
    private String imageUrl;
}
