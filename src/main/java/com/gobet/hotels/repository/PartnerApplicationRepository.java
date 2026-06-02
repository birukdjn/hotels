package com.gobet.hotels.repository;

import com.gobet.hotels.entity.PartnerApplication;
import com.gobet.hotels.enums.PartnerApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartnerApplicationRepository
        extends JpaRepository<PartnerApplication, Long> {

    List<PartnerApplication> findByStatus(PartnerApplicationStatus status);

    boolean existsByEmail(String email);

    boolean existsByTinNumber(String tinNumber);
}