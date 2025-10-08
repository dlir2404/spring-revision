package com.larry.spring.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.larry.spring.dto.request.UserCreationRequest;
import com.larry.spring.entity.User;
import com.larry.spring.repository.UserRepository;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private UserCreationRequest request;
    private User user;

    private LocalDate dob;

    @BeforeEach
    void initData() {
        dob = LocalDate.of(2000, 1, 1);

        request = UserCreationRequest.builder()
                .name("john")
                .firstName("John")
                .lastName("Doe")
                .password("12345678")
                .dob(dob)
                .build();

        user = User.builder()
                .id("123456789")
                .name("john")
                .firstName("John")
                .lastName("Doe")
                .dob(dob)
                .password("hashedpassword")
                .build();
    }

    @Test
    void createUser_valid_success() {
        // GIVEN
        when(userRepository.existsByName(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);

        // WHEN
        var response = userService.createRequest(request);

        Assertions.assertThat(response.getId()).isEqualTo("123456789");
        Assertions.assertThat(response.getName()).isEqualTo("john");
        Assertions.assertThat(response.getFirstName()).isEqualTo("John");
        Assertions.assertThat(response.getLastName()).isEqualTo("Doe");
        Assertions.assertThat(response.getDob()).isEqualTo(dob);
    }
}
