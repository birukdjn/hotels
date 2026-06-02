package com.gobet.hotels.service;

import com.gobet.hotels.dto.partner.CreatePartnerApplicationRequest;
import com.gobet.hotels.entity.PartnerApplication;
import com.gobet.hotels.repository.PartnerApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PartnerApplicationService
        implements IPartnerApplicationService {

    private final PartnerApplicationRepository repository;

    @Override
    public void createApplication(
            CreatePartnerApplicationRequest request
    ) {

        if (repository.existsByEmail(request.getEmail())) {
            throw new RuntimeException(
                    "Application already exists"
            );
        }

        PartnerApplication app = new PartnerApplication();

        app.setContactFirstName(request.getContactFirstName());
        app.setContactLastName(request.getContactLastName());
        app.setEmail(request.getEmail());
        app.setPhoneNumber(request.getPhoneNumber());
        app.setHotelName(request.getHotelName());
        app.setTinNumber(request.getTinNumber());
        app.setBusinessRegistrationNumber(
                request.getBusinessRegistrationNumber()
        );
        app.setLogoUrl(request.getLogoUrl());
        app.setBusinessLicenseUrl(
                request.getBusinessLicenseUrl()
        );
        app.setAddress(request.getAddress());

        repository.save(app);
    }
}