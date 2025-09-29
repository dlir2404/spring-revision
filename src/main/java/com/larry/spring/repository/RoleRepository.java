package com.larry.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.larry.spring.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    
}
