package com.springboot.blogapp.services;

import java.util.List;

import com.springboot.blogapp.dto.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, Long postId, Integer userId);
    void deleteComment(Integer commentId);
    CommentDto updateComment(CommentDto commentDto, Integer commentId);
    List<CommentDto> getCommentsByUser(Integer userId);
    List<CommentDto> getCommentsByPost(Long postId);
}
