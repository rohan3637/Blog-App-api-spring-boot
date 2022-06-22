package com.springboot.blogapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blogapp.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    //
}
