package com.gobet.hotels.dto.guest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuestResponse {

    private Long id;

    private Long hotelId;

    private String fullName;

    private String phone;

    private String email;

    private String nationality;

    private String nationalId;
}