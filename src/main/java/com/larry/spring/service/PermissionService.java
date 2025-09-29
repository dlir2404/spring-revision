package com.larry.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.larry.spring.dto.request.PermissionRequest;
import com.larry.spring.dto.response.PermissionResponse;
import com.larry.spring.entity.Permission;
import com.larry.spring.mapper.PermissionMapper;
import com.larry.spring.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionMapper permissionMapper;
    PermissionRepository permissionRepository;

    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);

        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAllPermissions() {
        List<Permission> permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    public  void deletePermission(String permission) {
        permissionRepository.deleteById(permission);
    }
}
