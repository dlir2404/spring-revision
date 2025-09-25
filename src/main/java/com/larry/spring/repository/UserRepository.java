package com.larry.spring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.larry.spring.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
    boolean existsByName(String name);
    Optional<User> findByName(String name);
}
