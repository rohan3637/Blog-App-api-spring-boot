package com.springboot.blogapp.services;

import java.util.List;

import com.springboot.blogapp.dto.PostDto;
import com.springboot.blogapp.dto.PostResponse;

public interface PostService {
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
    PostDto updatePost(PostDto postDto, Long postId);
    void deletePost(Long postId);
    PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    PostDto getPostById(Long postId);
    List<PostDto> getPostsByCategory(Integer categoryId);
    List<PostDto> getPostsByUser(Integer userId);
    List<PostDto> searchPost(String keyword);
}
