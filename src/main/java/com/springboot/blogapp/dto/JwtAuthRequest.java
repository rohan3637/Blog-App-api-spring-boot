package com.springboot.blogapp.dto;

import lombok.Data;

@Data
public class JwtAuthRequest {
    private String email;
    private String password;
}
