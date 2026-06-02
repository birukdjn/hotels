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

    @Column(nullable = false)
    private String ContactFirstName;

    @Column(nullable = false)
    private String ContactLastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String hotelName;

    @Column(nullable = false)
    private String tinNumber;

    @Column(nullable = false)
    private String businessRegistrationNumber;

    private String logoUrl;

    private String businessLicenseUrl;

    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PartnerApplicationStatus status = PartnerApplicationStatus.PENDING;

    private String reviewNote;

    private String rejectionReason;
}