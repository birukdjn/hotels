package com.gobet.hotels.entity;
import com.gobet.hotels.entity.common.HotelAwareEntity;
import com.gobet.hotels.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"email"})
        }
)
public class User extends HotelAwareEntity {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role = UserRole.RECEPTIONIST;

    private String resetToken;
    private LocalDateTime resetTokenExpiry;

    // Getters and Setters
}