package com.larry.spring.dto.request;

import java.time.LocalDate;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UpdateUserRequest {
    String name;
    String password;
    String firstName;
    String lastName;
    LocalDate dob;
    Set<String> roles;
}
