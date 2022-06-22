package com.springboot.blogapp.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private long id;
    private String title;
    private String content;
    private String image;
    private Date addedDate;
    private UserDto user;
    private CategoryDto category;
    private List<CommentDto> comments = new ArrayList<>();

}
