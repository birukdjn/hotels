package com.gobet.hotels.dto.hotel;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateHotelRequest {

    @NotBlank(message = "Hotel name is required")
    private String name;

    @Email(message = "Invalid email format")
    private String email;

    private String phone;
    private String address;
    private String city;
    private String logoUrl;

    private Boolean active;
}