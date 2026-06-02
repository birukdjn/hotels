package com.gobet.hotels.service;

import com.gobet.hotels.dto.partner.CreatePartnerApplicationRequest;

public interface IPartnerApplicationService {

    void createApplication(
            CreatePartnerApplicationRequest request
    );
}