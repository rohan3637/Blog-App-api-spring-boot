package com.springboot.blogapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blogapp.dto.ApiResponse;
import com.springboot.blogapp.dto.CommentDto;
import com.springboot.blogapp.services.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {
    
    @Autowired
    private CommentService commentService;

    //POST - create post
    @PostMapping("/user/{userId}/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Long postId, @PathVariable Integer userId){
        CommentDto createdCommentDto = commentService.createComment(commentDto, postId, userId);
        return new ResponseEntity<>(createdCommentDto, HttpStatus.CREATED);
    }

    //DELETE - delete post
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
        commentService.deleteComment(commentId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted successfully !!", true), HttpStatus.OK);
    }
 
    //PUT - update comment by Id
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto, @PathVariable Integer commentId){
        CommentDto updatedCommentDto = commentService.updateComment(commentDto, commentId);
        return new ResponseEntity<CommentDto>(updatedCommentDto, HttpStatus.OK);
    }

    //GET - get comments by Post
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByPost(@PathVariable Long postId){
        List<CommentDto> commentDtos = commentService.getCommentsByPost(postId);
        return new ResponseEntity<>(commentDtos, HttpStatus.OK);
    }

    //GET - get comments by user
    @GetMapping("/users/{userId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByUser(@PathVariable Integer userId){
        List<CommentDto> commentDtos = commentService.getCommentsByUser(userId);
        return new ResponseEntity<>(commentDtos, HttpStatus.OK);
    }

}
