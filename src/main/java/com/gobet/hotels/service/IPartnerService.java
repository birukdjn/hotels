package com.gobet.hotels.service;

import com.gobet.hotels.dto.partner.PartnerApplicationRequest;
import com.gobet.hotels.entity.PartnerApplication;

import java.util.List;

public interface IPartnerService {

    PartnerApplication submitApplication(PartnerApplicationRequest request);

    List<PartnerApplication> getAllApplications();

    void approve(Long id);

    void reject(Long id);
}