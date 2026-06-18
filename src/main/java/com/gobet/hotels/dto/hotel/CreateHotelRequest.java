package com.gobet.hotels.dto.hotel;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateHotelRequest {

    @NotBlank(message = "Hotel name is required")
    private String name;

    private String phone;

    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Region is required")
    private String Region;

    @NotBlank(message = "Zone is required")
    private String Zone;

    @NotBlank(message = "City is required")
    private String city;

    private String address;

    private String ImageUrl;
    private String logoUrl;
}