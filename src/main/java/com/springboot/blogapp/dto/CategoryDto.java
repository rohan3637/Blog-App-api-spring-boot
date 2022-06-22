package com.springboot.blogapp.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDto {

    private Integer categoryId;

    @NotBlank()
    @Size(min = 3, message = "Title should be more than 3 characters.")
    private String categoryTitle;

    @NotBlank
    @Size(max = 500, message = "Description should be less than 50 characters.")
    private String categoryDescription;
}
