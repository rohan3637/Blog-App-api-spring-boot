package com.springboot.blogapp.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.blogapp.dao.CategoryRepository;
import com.springboot.blogapp.dao.PostRepository;
import com.springboot.blogapp.dao.UserRepository;
import com.springboot.blogapp.dto.PostDto;
import com.springboot.blogapp.dto.PostResponse;
import com.springboot.blogapp.entities.Category;
import com.springboot.blogapp.entities.Post;
import com.springboot.blogapp.entities.User;
import com.springboot.blogapp.exception.ResourceNotFoundException;
import com.springboot.blogapp.services.PostService;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));            
        Post post = modelMapper.map(postDto, Post.class);
        post.setImage("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post savedPost = postRepository.save(post);
        PostDto savedPostDto = modelMapper.map(savedPost, PostDto.class);
        return savedPostDto;
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long postId) {
        Post oldPost = postRepository.findById(postId)
                    .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
        oldPost.setTitle(postDto.getTitle());
        oldPost.setContent(postDto.getContent());
        oldPost.setImage(postDto.getImage());
        Post updatedPost = postRepository.save(oldPost);
        PostDto updatedPostDto = modelMapper.map(updatedPost, PostDto.class);
        return updatedPostDto;
    }

    @Override
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
        postRepository.delete(post);            
    }

    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        //pagination and sorting implemented
        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pages = postRepository.findAll(pageable);
        List<Post> posts = postRepository.findAll(pageable).getContent();
        List<PostDto> postDtos = new ArrayList<>();
        for(Post post : posts){
            postDtos.add(modelMapper.map(post, PostDto.class));
        }         
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pages.getNumber());
        postResponse.setPageSize(pages.getSize());
        postResponse.setTotalElements(pages.getTotalElements()); 
        postResponse.setTotalPages(pages.getTotalPages());
        postResponse.setLastPage(pages.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
        PostDto postDto = modelMapper.map(post, PostDto.class);            
        return postDto;
    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
        List<Post> posts = postRepository.findByCategory(category);   
        List<PostDto> postDtos = new ArrayList<>();
        for(Post post : posts){
            postDtos.add(modelMapper.map(post, PostDto.class));
        }         
        return postDtos;
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
        User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        List<Post> posts = postRepository.findByUser(user);     
        List<PostDto> postDtos = new ArrayList<>();
        for(Post post : posts){
            postDtos.add(modelMapper.map(post, PostDto.class));
        }         
        return postDtos;       
    }

    @Override
    public List<PostDto> searchPost(String keyword) {
        List<Post> posts = postRepository.findByTitleContaining(keyword);
        //List<Post> posts = postRepository.searchByTitle("%"+keyword+"%");
        List<PostDto> postDtos = new ArrayList<>();
        for(Post post : posts){
            postDtos.add(modelMapper.map(post, PostDto.class));
        } 
        return postDtos;
    }
    
}
