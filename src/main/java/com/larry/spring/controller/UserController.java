package com.larry.spring.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.larry.spring.dto.request.ApiResponse;
import com.larry.spring.dto.request.UpdateUserRequest;
import com.larry.spring.dto.request.UserCreationRequest;
import com.larry.spring.dto.response.UserResponse;
import com.larry.spring.entity.User;
import com.larry.spring.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/users")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserController {
    UserService userService;

    @PostMapping
    public ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request) {
        ApiResponse<User> user = new ApiResponse<>();
        user.setResult(userService.createRequest(request));
        return user;
    }
    
    @GetMapping
    public ApiResponse<List<User>> getAllUsers() {
        ApiResponse<List<User>> response = new ApiResponse<>();
        response.setResult(userService.getAllUsers());
        return response;
    }
 
    @GetMapping("/{id}")
    @PostAuthorize("returnObject.result.id == authentication.name")
    public ApiResponse<User> getUserById(@PathVariable String id) {
        ApiResponse<User> response = new ApiResponse<>();
        response.setResult(userService.getUserById(id));
        return response;
    }

    @GetMapping("/myinfo")
    public ApiResponse<UserResponse> getMyInfo() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        
        var userInfo = userService.getMyInfo(authentication.getName());
        return ApiResponse.<UserResponse>builder().result(userInfo).build();
    }
    

    @PutMapping("/{id}")
    public ApiResponse<User> updateUser(@PathVariable String id, @RequestBody UpdateUserRequest request) {
        ApiResponse<User> response = new ApiResponse<>();
        response.setResult(userService.updateUser(id, request));
        return response;
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        ApiResponse<String> response = new ApiResponse<>();
        response.setResult("Deleted Successfully");
        return response;
    }
}
