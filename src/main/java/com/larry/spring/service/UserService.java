package com.larry.spring.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.larry.spring.dto.request.UpdateUserRequest;
import com.larry.spring.dto.request.UserCreationRequest;
import com.larry.spring.dto.response.UserResponse;
import com.larry.spring.entity.User;
import com.larry.spring.enums.Roles;
import com.larry.spring.exception.AppException;
import com.larry.spring.exception.ErrorCode;
import com.larry.spring.mapper.UserMapper;
import com.larry.spring.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public User createRequest(UserCreationRequest request) {
        if (userRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
        }

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Roles.USER.name());
        // user.setRoles(roles);

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserResponse getMyInfo(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserResponse(user);
    }

    public User updateUser(String id, UpdateUserRequest request) {
        User user = getUserById(id);

        userMapper.updateUser(user, request);

        return userRepository.save(user);
    }

    public void deleteUser(String id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
}
