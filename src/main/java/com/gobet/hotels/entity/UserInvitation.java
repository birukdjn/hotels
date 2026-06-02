package com.gobet.hotels.entity;

import com.gobet.hotels.entity.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "user_invitations")
public class UserInvitation extends BaseEntity {

    private String email;
    private String token;

    private Long hotelId;
    private String role;

    private LocalDateTime expiryDate;
    private boolean used = false;
}