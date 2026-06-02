package com.gobet.hotels.service;

import com.gobet.hotels.entity.Hotel;
import com.gobet.hotels.entity.PartnerApplication;
import com.gobet.hotels.entity.User;
import com.gobet.hotels.enums.PartnerApplicationStatus;
import com.gobet.hotels.enums.UserRole;
import com.gobet.hotels.repository.HotelRepository;
import com.gobet.hotels.repository.PartnerApplicationRepository;
import com.gobet.hotels.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PartnerApplicationAdminService
        implements IPartnerApplicationAdminService {

    private final PartnerApplicationRepository partnerApplicationRepository;
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<PartnerApplication> getAll() {
        return partnerApplicationRepository.findAll();
    }

    @Override
    public void approve(Long applicationId) {

        PartnerApplication application =
                partnerApplicationRepository.findById(applicationId)
                        .orElseThrow(() ->
                                new RuntimeException("Application not found"));

        if (application.getStatus() != PartnerApplicationStatus.PENDING) {
            throw new RuntimeException("Application already processed");
        }

        // Create Hotel
        Hotel hotel = new Hotel();
        hotel.setName(application.getHotelName());
        hotel.setEmail(application.getEmail());
        hotel.setPhone(application.getPhoneNumber());

        hotel = hotelRepository.save(hotel);

        // Generate temporary password
        String temporaryPassword =
                UUID.randomUUID()
                        .toString()
                        .replace("-", "")
                        .substring(0, 10);

        // Create Hotel Admin
        User user = new User();
        user.setFirstName(application.getContactFirstName());
        user.setLastName(application.getContactLastName());
        user.setEmail(application.getEmail());
        user.setPasswordHash(
                passwordEncoder.encode(temporaryPassword)
        );
        user.setRole(UserRole.HOTEL_ADMIN);
        user.setHotel(hotel);

        userRepository.save(user);

        application.setStatus(PartnerApplicationStatus.APPROVED);

        partnerApplicationRepository.save(application);

        // TODO:
        // Send email containing:
        // Email = application.getEmail()
        // Temporary Password = temporaryPassword
    }

    @Override
    public void reject(Long applicationId, String reason) {

        PartnerApplication application =
                partnerApplicationRepository.findById(applicationId)
                        .orElseThrow(() ->
                                new RuntimeException("Application not found"));

        if (application.getStatus() != PartnerApplicationStatus.PENDING) {
            throw new RuntimeException("Application already processed");
        }

        application.setStatus(PartnerApplicationStatus.REJECTED);
        application.setRejectionReason(reason);

        partnerApplicationRepository.save(application);

        // TODO:
        // Send rejection email with reason
    }
}