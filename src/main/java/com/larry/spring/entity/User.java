package com.larry.spring.entity;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String name;
    String password;
    String firstName;
    String lastName;
    LocalDate dob;
    Set<String> roles;
}
