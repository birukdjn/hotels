package com.gobet.hotels.dto.partner;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartnerApplicationRequest {
    private String fullName;
    private String email;
    private String phone;

    private String hotelName;
    private String businessRegistrationNumber;
    private String tinNumber;

    private String logoUrl;
    private String documents;
}