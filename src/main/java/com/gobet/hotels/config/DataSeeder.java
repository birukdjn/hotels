package com.gobet.hotels.config;

import com.gobet.hotels.entity.User;
import com.gobet.hotels.enums.UserRole;
import com.gobet.hotels.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataSeeder {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner seedUsers() {
        return args -> {

            createUserIfNotExists(
                    "Super",
                    "Admin",
                    "superadmin@gobet.org",
                    UserRole.SUPER_ADMIN
            );

            createUserIfNotExists(
                    "Hotel",
                    "Admin",
                    "hoteladmin@gobet.org",
                    UserRole.HOTEL_ADMIN
            );

            createUserIfNotExists(
                    "Front",
                    "Desk",
                    "receptionist@gobet.org",
                    UserRole.RECEPTIONIST
            );

            createUserIfNotExists(
                    "Finance",
                    "Officer",
                    "accountant@gobet.org",
                    UserRole.ACCOUNTANT
            );
        };
    }

    private void createUserIfNotExists(
            String firstName,
            String lastName,
            String email,
            UserRole role) {

        if (userRepository.findByEmail(email).isPresent()) {
            return;
        }

        User user = new User();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);

        user.setPasswordHash(
                passwordEncoder.encode("Password@123")
        );

        user.setRole(role);

        userRepository.save(user);
    }
}