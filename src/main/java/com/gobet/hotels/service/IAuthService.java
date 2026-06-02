package com.gobet.hotels.service;

import com.gobet.hotels.dto.auth.*;
import org.springframework.security.core.Authentication;

public interface IAuthService {

    AuthResponse login(LoginRequest request);

    AuthResponse register(RegisterRequest request);

    MeResponse me(Authentication authentication);

    MeResponse updateProfile(Authentication authentication, UpdateProfileRequest request);

    String forgotPassword(ForgotPasswordRequest request);

    String resetPassword(ResetPasswordRequest request);

    String changePassword(Authentication authentication, ChangePasswordRequest request);
}
