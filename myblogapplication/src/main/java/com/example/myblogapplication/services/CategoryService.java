package com.example.myblogapplication.services;
import com.example.myblogapplication.payloads.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CategoryService {

    //create
    CategoryDto createCategory(CategoryDto categoryDto);
    //update
    CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);

    //delete
    void deleteCategory(Integer categoryId);
    //getById
    CategoryDto getCategoryById(Integer categoryId);
    //get-List-of-Categories
    List<CategoryDto> getAllCategories();

}
