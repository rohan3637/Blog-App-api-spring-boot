package com.springboot.blogapp.services;

import java.util.List;

import com.springboot.blogapp.dto.CategoryDto;

public interface CategoryService { 
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
    void deleteCategory(Integer categoryId);
    CategoryDto getCategory(Integer categoryId);
    List<CategoryDto> getAllCategories();
}
