package com.springboot.blogapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blogapp.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    //
}
