package com.gobet.hotels.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileRequest {
    private String firstName;
    private String lastName;
}