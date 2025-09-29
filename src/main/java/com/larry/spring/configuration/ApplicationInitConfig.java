package com.larry.spring.configuration;

import java.util.HashSet;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.larry.spring.entity.User;
import com.larry.spring.enums.Roles;
import com.larry.spring.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Configuration
@Slf4j
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return _ -> {
            if (userRepository.findByName("admin").isEmpty()) {
                var roles = new HashSet<String>();
                roles.add(Roles.ADMIN.name());

                User admin = User.builder()
                    .name("admin")
                    .firstName("Admin")
                    .lastName("User")
                    // .roles(roles)
                    .password(passwordEncoder.encode("admin"))
                    .build();

                userRepository.save(admin);
                log.warn("Admin user created with username 'admin' and password 'admin'. Please change the password immediately.");
            }
        };  
    }
}
