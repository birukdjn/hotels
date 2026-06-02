package com.gobet.hotels.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetPasswordRequest {
    private String token;
    private String password;
}