package com.gobet.hotels.dto.hotel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HotelResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String logoUrl;
    private Boolean active;
}