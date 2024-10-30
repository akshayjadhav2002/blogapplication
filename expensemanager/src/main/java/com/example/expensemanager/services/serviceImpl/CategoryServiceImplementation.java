package com.example.expensemanager.services.serviceImpl;

import com.example.expensemanager.dto.ApiResponse;
import com.example.expensemanager.dto.CategoryDTO;
import com.example.expensemanager.entity.Category;
import com.example.expensemanager.repository.CategoryRepository;
import com.example.expensemanager.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class CategoryServiceImplementation  implements CategoryService {

    private CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    final static Logger logger = LoggerFactory.getLogger(CategoryServiceImplementation.class);
    CategoryServiceImplementation(CategoryRepository categoryRepository,ModelMapper modelMapper){
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public Boolean createCategory(CategoryDTO categoryDTO) {
        if(!ObjectUtils.isEmpty(categoryDTO)){
            Category category = modelMapper.map(categoryDTO,Category.class);
            Category savedCategory = categoryRepository.save(category);
            logger.info("Category saved in database - {}",savedCategory);
            return true;
        }
        else{
            logger.info("fail to save the Category in Database");
            return false;
        }
    }

    @Override
    public Object updateCategory(CategoryDTO categoryDTO) {
        if (!ObjectUtils.isEmpty(categoryDTO)){
            Category savedCategotry = categoryRepository.getReferenceById(categoryDTO.getCategoryId());
            savedCategotry.setCategory(categoryDTO.getCategory());
            savedCategotry.setCategoryDesc(categoryDTO.getCategoryDesc());
            savedCategotry.setImageUrl(categoryDTO.getImageUrl());
            logger.info("Category update in database successfully - {}",savedCategotry);
            return categoryRepository.save(savedCategotry);

        }
        else {
            logger.info("Category update failed");
            return new ApiResponse( categoryDTO.getCategory() + " not Found",false);
        }
    }

    @Override
    public Boolean deleteCategory(Integer categoryId) {
        try {
            Category category = categoryRepository.getReferenceById(categoryId);
            if (!ObjectUtils.isEmpty(category)) {
                categoryRepository.delete(category);
                logger.info("Category deleted successfully from Database - {}", categoryId);
                return true;
            } else {
                logger.info("Category failed to delete");
                return false;
            }
        }
        catch (Exception exception){
            logger.warn(exception.getMessage());
            return false;
        }
    }

    @Override
    public List<Category> getAllCategory(String userName) {
        List<Category> categoriesList = categoryRepository.findAll().stream().filter(category -> category.getUserName().equals(userName)).toList();
        logger.info("All category list fetched from database");
        return categoriesList;
    }

    @Override
    public Object getCategoryById(Integer categoryId) {
        try {
            Category category = categoryRepository.getReferenceById(categoryId);
            logger.info("Category fetched from Database successfully - {}", category);
            return category;
        }
        catch (Exception exception){
            logger.warn(exception.getMessage());
            return new ApiResponse("Exception occurred Category Not Found",false);
        }
    }
}
