package com.larry.spring.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.larry.spring.dto.request.UpdateUserRequest;
import com.larry.spring.dto.request.UserCreationRequest;
import com.larry.spring.dto.response.UserResponse;
import com.larry.spring.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    UserResponse toUserResponse(User user);
    void updateUser(@MappingTarget User user, UpdateUserRequest request);
}
