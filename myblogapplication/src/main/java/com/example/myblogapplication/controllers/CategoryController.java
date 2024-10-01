package com.example.myblogapplication.controllers;
import com.example.myblogapplication.payloads.ApiResponse;
import com.example.myblogapplication.payloads.CategoryDto;
import com.example.myblogapplication.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    //create
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
        return  new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);
    }
    //update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Integer categoryId){
        CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto,categoryId);
        return  new ResponseEntity<CategoryDto>(updatedCategory, HttpStatus.OK);
    }
    //delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId){
         this.categoryService.deleteCategory(categoryId);
        return  new ResponseEntity<ApiResponse>(new ApiResponse("Successfully deleted Category",true),HttpStatus.OK);
    }
    //getbyId
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById (@PathVariable Integer categoryId){
       CategoryDto categoryDto =  this.categoryService.getCategoryById(categoryId);
        return  new ResponseEntity<CategoryDto>(categoryDto,HttpStatus.OK);
    }
    //getAll
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategories (){
        List<CategoryDto> categoryList =  this.categoryService.getAllCategories();
        return  new ResponseEntity<List<CategoryDto>>(categoryList,HttpStatus.OK);
    }
}
