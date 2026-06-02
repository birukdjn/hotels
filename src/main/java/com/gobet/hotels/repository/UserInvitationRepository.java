package com.gobet.hotels.repository;

import com.gobet.hotels.entity.UserInvitation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInvitationRepository extends JpaRepository<UserInvitation, Long> {
    Optional<UserInvitation> findByToken(String token);
}