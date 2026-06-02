package com.gobet.hotels.service;

import com.gobet.hotels.entity.PartnerApplication;

import java.util.List;

public interface IPartnerApplicationAdminService {

    List<PartnerApplication> getAll();

    void approve(Long applicationId);

    void reject(Long applicationId, String reason);
}