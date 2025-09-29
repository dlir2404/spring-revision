package com.larry.spring.mapper;


import org.mapstruct.Mapper;

import com.larry.spring.dto.request.PermissionRequest;
import com.larry.spring.dto.response.PermissionResponse;
import com.larry.spring.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}
