package com.larry.spring.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.larry.spring.dto.request.UpdateUserRequest;
import com.larry.spring.dto.request.UserCreationRequest;
import com.larry.spring.dto.response.UserResponse;
import com.larry.spring.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "id", ignore = true)
    User toUser(UserCreationRequest request);
    
    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateUser(@MappingTarget User user, UpdateUserRequest request);
}
