package com.springboot.blogapp.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.blogapp.dao.CommentRepository;
import com.springboot.blogapp.dao.PostRepository;
import com.springboot.blogapp.dao.UserRepository;
import com.springboot.blogapp.dto.CommentDto;
import com.springboot.blogapp.entities.Comment;
import com.springboot.blogapp.entities.Post;
import com.springboot.blogapp.entities.User;
import com.springboot.blogapp.exception.ResourceNotFoundException;
import com.springboot.blogapp.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Long postId, Integer userId) {
        Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));  
        User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));              
        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        comment.setUser(user);
        Comment savedComment = commentRepository.save(comment);
        CommentDto savedCommentDto = modelMapper.map(savedComment, CommentDto.class);
        return savedCommentDto;
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = commentRepository.findById(commentId)
                          .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        commentRepository.delete(comment);                            
    }
 
    @Override
    public CommentDto updateComment(CommentDto commentDto, Integer commentId) {
        Comment oldComment = commentRepository.findById(commentId)
                          .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        oldComment.setContent(commentDto.getContent());
        Comment updatedComment = commentRepository.save(oldComment);
        CommentDto updatedCommentDto = modelMapper.map(updatedComment, CommentDto.class);                   
        return updatedCommentDto;
    }

    @Override
    public List<CommentDto> getCommentsByUser(Integer userId) {
        User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId)); 
        List<Comment> comments = commentRepository.findByUser(user);      
        List<CommentDto> commentDtos = new ArrayList<>();
        for(Comment comment : comments){
            commentDtos.add(modelMapper.map(comment, CommentDto.class));
        }      
        return commentDtos;
    }

    @Override
    public List<CommentDto> getCommentsByPost(Long postId) {
        Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId)); 
        List<Comment> comments = commentRepository.findByPost(post);      
        List<CommentDto> commentDtos = new ArrayList<>();
        for(Comment comment : comments){
            commentDtos.add(modelMapper.map(comment, CommentDto.class));
        }      
        return commentDtos;
    }
    
}
