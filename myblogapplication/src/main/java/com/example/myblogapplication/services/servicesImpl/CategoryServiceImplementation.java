package com.example.myblogapplication.services.servicesImpl;
import com.example.myblogapplication.entities.Category;
import com.example.myblogapplication.exceptions.ResourceNotFoundException;
import com.example.myblogapplication.payloads.CategoryDto;
import com.example.myblogapplication.repositories.CategoryRepository;
import com.example.myblogapplication.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImplementation implements CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modalMapper;

    @Autowired
    CategoryServiceImplementation (CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category =  categoryRepository.save(this.categoryDtoToCategory(categoryDto));
        return this.categoryToCategoryDto(category);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                ()-> new ResourceNotFoundException("Category","CategoryId",categoryId));
        category.setCategoryName(categoryDto.getCategoryName());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCategory = categoryRepository.save(category);
        return this.categoryToCategoryDto(updatedCategory);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(
                ()-> new ResourceNotFoundException("Category","categoryId",categoryId));
        this.categoryRepository.delete(category);
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(
                ()-> new ResourceNotFoundException("Category","categoryId",categoryId));
        return this.categoryToCategoryDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<CategoryDto> categoryDtoList = this.categoryRepository.findAll().stream().map(
                (category -> categoryToCategoryDto(category) )).collect(Collectors.toList());
        return categoryDtoList;
    }

    private Category categoryDtoToCategory(CategoryDto categoryDto){
        return this.modalMapper.map(categoryDto,Category.class);
    }

    private CategoryDto categoryToCategoryDto (Category category){
        return this.modalMapper.map(category,CategoryDto.class);
    }
}
