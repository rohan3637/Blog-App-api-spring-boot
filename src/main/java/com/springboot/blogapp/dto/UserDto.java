package com.springboot.blogapp.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private int id;

    @NotEmpty(message = "Name can't be empty.")
    @Size(min = 4, message = "Name must be greater than or equal to 4 characters !!")
    private String name;

    @Email(message = "Please enter valid Email !!")
    private String email;

    @NotEmpty
    @Size(min = 6, message = "Password must be min of 6 characters !!")
    private String password;

    @NotEmpty(message = "About can't be empty.")
    private String about; 

    private List<CommentDto> comments = new ArrayList<>();

    private List<RoleDto> roles = new ArrayList<>();

}
