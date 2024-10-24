package com.example.expensemanager.services;

import com.example.expensemanager.dto.CategoryDTO;
import com.example.expensemanager.entity.Category;

import java.util.List;

public interface CategoryService {
    Boolean createCategory(CategoryDTO categoryDTO);
    Object updateCategory(CategoryDTO categoryDTO);
    Boolean deleteCategory(Integer categoryId);
    List<Category> getAllCategorys();
    Object getCategoryById(Integer categoryId);
}
