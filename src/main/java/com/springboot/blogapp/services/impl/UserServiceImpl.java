package com.springboot.blogapp.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.blogapp.dao.RoleRepository;
import com.springboot.blogapp.dao.UserRepository;
import com.springboot.blogapp.dto.UserDto;
import com.springboot.blogapp.entities.Role;
import com.springboot.blogapp.entities.User;
import com.springboot.blogapp.exception.ResourceNotFoundException;
import com.springboot.blogapp.helper.ApiConstants;
import com.springboot.blogapp.services.UserService;

@Service
public  class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToEntity(userDto);
        User savedUser = this.userRepository.save(user);
        return entityToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = this.userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        user.setPassword(userDto.getPassword());    
        User updatedUser = this.userRepository.save(user);
        return entityToDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));            
        return this.entityToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> allUsers = this.userRepository.findAll();
        List<UserDto> allUsersDto = new ArrayList<>();
        for(User user : allUsers){
            allUsersDto.add(this.entityToDto(user));
        }
        return allUsersDto;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        this.userRepository.delete(user);            
    }

    private User dtoToEntity(UserDto userDto){
        User user = this.modelMapper.map(userDto, User.class);
        /*user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());*/
        return user;
    }

    private UserDto entityToDto(User user){
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        return userDto;
    }

    @Override
    public UserDto registerUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findById(ApiConstants.NORMAL_USER).get();
        user.getRoles().add(role);
        User newUser = userRepository.save(user);
        UserDto newUserDto = modelMapper.map(newUser, UserDto.class);
        return newUserDto;
    }
    
}
