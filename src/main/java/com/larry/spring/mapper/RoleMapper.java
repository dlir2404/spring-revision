package com.larry.spring.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.larry.spring.dto.request.RoleRequest;
import com.larry.spring.dto.response.RoleResponse;
import com.larry.spring.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
