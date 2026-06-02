package com.gobet.hotels.entity;

import com.gobet.hotels.entity.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "password_reset_tokens")
@Getter
@Setter
public class PasswordResetToken extends BaseEntity {

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private LocalDateTime expiryDate;
}
