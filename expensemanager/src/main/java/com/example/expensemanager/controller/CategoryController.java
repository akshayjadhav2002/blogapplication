package com.example.expensemanager.controller;

import com.example.expensemanager.dto.ApiResponse;
import com.example.expensemanager.dto.CategoryDTO;
import com.example.expensemanager.entity.Category;
import com.example.expensemanager.services.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/v1/category")
public class CategoryController {

    private CategoryService categoryService;

    final static Logger logger = LoggerFactory.getLogger(CategoryController.class);

    CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @PostMapping("/")
    public ResponseEntity<Object> createCategory(@RequestBody CategoryDTO categoryDTO, Principal principal){
        categoryDTO.setUserName(principal.getName());
        Boolean isCreated =  this.categoryService.createCategory(categoryDTO);
        if(isCreated) {
            logger.info("category created successfully");
            return new ResponseEntity(new ApiResponse("Category saved successfully", isCreated), HttpStatus.CREATED);
        }
        else{
            logger.info("category failed to create");
            return new ResponseEntity<>(new ApiResponse("Category failed to create",isCreated),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/lists/")
    public ResponseEntity<Object> getCategory(Principal principal) {
        String userName = principal.getName();
        List<Category> categoryList = this.categoryService.getAllCategory(userName);
        if (!ObjectUtils.isEmpty(categoryList)){
            logger.info("category list fetched  successfully"); 
            return new ResponseEntity<>(categoryList, HttpStatus.OK);
        }
        else{
            logger.info("category list failed to fetched");
            return new ResponseEntity<>(new ApiResponse("Fail to get all list",false),HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("{categoryId}")
    public ResponseEntity<Object> getCategoryById(@PathVariable Integer categoryId){
        if (categoryId<=0){
            return new ResponseEntity<>(new ApiResponse("Please Enter Valid CategoryId",false),HttpStatus.BAD_REQUEST);
        }
        else {
            try {
                Category category =(Category) categoryService.getCategoryById(categoryId);
                return new ResponseEntity<>(category,HttpStatus.OK);
            }
            catch (Exception exception){
                logger.error(exception.getMessage());
                return new ResponseEntity<>(new ApiResponse("Category not found with id - " + categoryId,false),HttpStatus.NOT_FOUND);
            }
        }
    }

    @DeleteMapping("delete/{categoryId}")
    public ResponseEntity<Object> deleteCategory(@PathVariable Integer categoryId){
        Boolean isDeleted = categoryService.deleteCategory(categoryId);
        if (isDeleted){
            return new ResponseEntity<>(new ApiResponse("Category deleted Successfully",true),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(new ApiResponse("Category failed to delete",false),HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("update/")
    public ResponseEntity<Object> updateCategory(@RequestBody CategoryDTO categoryDTO){
        try {
            Category category = (Category)categoryService.updateCategory(categoryDTO);
            return new ResponseEntity<>(category,HttpStatus.ACCEPTED);
        }
        catch (Exception exception){
            logger.warn(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse("Not a valid Category",false),HttpStatus.BAD_REQUEST);
        }


    }
}
