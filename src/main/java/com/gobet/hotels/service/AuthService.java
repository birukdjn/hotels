package com.gobet.hotels.service;

import com.gobet.hotels.dto.auth.*;
import com.gobet.hotels.entity.User;
import com.gobet.hotels.enums.UserRole;
import com.gobet.hotels.exception.AuthenticationException;
import com.gobet.hotels.repository.UserRepository;
import com.gobet.hotels.security.JwtUtil;
import com.gobet.hotels.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new AuthenticationException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        AuthResponse res = new AuthResponse();
        res.setToken(token);
        res.setRole(user.getRole().name());

        return res;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.RECEPTIONIST);

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        AuthResponse res = new AuthResponse();
        res.setToken(token);
        res.setRole(user.getRole().name());

        return res;
    }

    @Override
    public MeResponse me(Authentication authentication) {

        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        User user = securityUser.getUser();

        return new MeResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole().name(),
                user.getHotel() != null ? user.getHotel().getId() : null
        );
    }

    @Override
    public MeResponse updateProfile(Authentication authentication, UpdateProfileRequest request) {

        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        User user = securityUser.getUser();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        userRepository.save(user);

        return new MeResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole().name(),
                user.getHotel() != null ? user.getHotel().getId() : null
        );
    }

    @Override
    public String forgotPassword(ForgotPasswordRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = UUID.randomUUID().toString();

        user.setResetToken(token);
        user.setResetTokenExpiry(LocalDateTime.now().plusMinutes(15));

        userRepository.save(user);

        // TODO: send email (SMTP)
        return token;
    }

    @Override
    public String resetPassword(ResetPasswordRequest request) {

        User user = userRepository.findByResetToken(request.getToken())
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }

        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);

        userRepository.save(user);

        return "Password reset successful";
    }

    @Override
    public String changePassword(Authentication authentication, ChangePasswordRequest request) {

        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        User user = securityUser.getUser();

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Old password is incorrect");
        }

        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        return "Password changed successfully";
    }




}