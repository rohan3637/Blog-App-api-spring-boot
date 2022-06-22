package com.springboot.blogapp.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.blogapp.dao.CategoryRepository;
import com.springboot.blogapp.dto.CategoryDto;
import com.springboot.blogapp.entities.Category;
import com.springboot.blogapp.exception.ResourceNotFoundException;
import com.springboot.blogapp.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        Category addedCategory = categoryRepository.save(category);
        CategoryDto addedCategoryDto = modelMapper.map(addedCategory, CategoryDto.class); 
        return addedCategoryDto;
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category oldCategory = categoryRepository.findById(categoryId)
                                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
        oldCategory.setCategoryTitle(categoryDto.getCategoryTitle());
        oldCategory.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCategory = categoryRepository.save(oldCategory);                        
        CategoryDto updatedCategoryDto = modelMapper.map(updatedCategory, CategoryDto.class);
        return updatedCategoryDto;
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
        categoryRepository.delete(category);                        
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));  
        CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
        return categoryDto;
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> allCategory = categoryRepository.findAll();
        List<CategoryDto> allCategoryDto = new ArrayList<>();
        for(Category category : allCategory){
            allCategoryDto.add(modelMapper.map(category, CategoryDto.class));
        }
        return allCategoryDto;
    }
    
}
