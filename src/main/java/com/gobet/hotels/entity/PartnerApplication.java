package com.gobet.hotels.entity;

import com.gobet.hotels.entity.common.BaseEntity;
import com.gobet.hotels.enums.PartnerApplicationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "partner_applications")
public class PartnerApplication extends BaseEntity {

    private String fullName;
    private String email;
    private String phone;

    private String hotelName;
    private String businessRegistrationNumber;
    private String tinNumber;

    private String logoUrl;

    @Lob
    private String documents; // JSON or file URLs

    @Enumerated(EnumType.STRING)
    private PartnerApplicationStatus status = PartnerApplicationStatus.PENDING;
}