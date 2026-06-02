package com.gobet.hotels.dto.partner;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePartnerApplicationRequest {

    @NotBlank
    private String contactFirstName;

    @NotBlank
    private String contactLastName;

    @Email
    private String email;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String hotelName;

    @NotBlank
    private String tinNumber;

    @NotBlank
    private String businessRegistrationNumber;

    private String logoUrl;

    private String businessLicenseUrl;

    private String address;
}