package com.larry.spring.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

import com.larry.spring.validator.DobConstraint;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UserCreationRequest {
    String name;
    
    @Size(min = 8, message = "Password must be at least 8 characters long")
    String password;
    String firstName;
    String lastName;

    @DobConstraint(min = 18)
    LocalDate dob;
}
