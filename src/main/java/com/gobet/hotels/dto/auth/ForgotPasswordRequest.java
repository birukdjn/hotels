package com.gobet.hotels.dto.auth;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgotPasswordRequest {
    @Email
    private String email;
}
