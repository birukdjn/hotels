package com.gobet.hotels.dto.guest;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateGuestRequest {

    @NotBlank
    private String fullName;

    private String phone;

    private String email;

    private String nationality;

    private String nationalId;
}