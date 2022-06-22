package com.springboot.blogapp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blogapp.entities.Comment;
import com.springboot.blogapp.entities.Post;
import com.springboot.blogapp.entities.User;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByUser(User user);
    List<Comment> findByPost(Post post);
}
