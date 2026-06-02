package com.gobet.hotels.service;

import com.gobet.hotels.dto.partner.PartnerApplicationRequest;
import com.gobet.hotels.entity.*;
import com.gobet.hotels.enums.PartnerApplicationStatus;
import com.gobet.hotels.enums.UserRole;
import com.gobet.hotels.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PartnerService implements IPartnerService {

    private final PartnerApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final UserInvitationRepository invitationRepository;

    @Override
    public PartnerApplication submitApplication(PartnerApplicationRequest request) {

        PartnerApplication app = new PartnerApplication();
        app.setFullName(request.getFullName());
        app.setEmail(request.getEmail());
        app.setPhone(request.getPhone());
        app.setHotelName(request.getHotelName());
        app.setBusinessRegistrationNumber(request.getBusinessRegistrationNumber());
        app.setTinNumber(request.getTinNumber());
        app.setLogoUrl(request.getLogoUrl());
        app.setDocuments(request.getDocuments());
        app.setStatus(PartnerApplicationStatus.PENDING);

        return applicationRepository.save(app);
    }

    @Override
    public List<PartnerApplication> getAllApplications() {
        return applicationRepository.findAll();
    }

    @Override
    public void approve(Long id) {

        PartnerApplication app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        app.setStatus(PartnerApplicationStatus.APPROVED);
        applicationRepository.save(app);

        // 1. Create Hotel (simplified placeholder)
        Hotel hotel = new Hotel();
        hotel.setName(app.getHotelName());

        // assume hotelRepository exists
//         hotelRepository.save(hotel);

        // 2. Create User (HOTEL_ADMIN)
        User user = new User();
        user.setEmail(app.getEmail());
        user.setFirstName(app.getFullName());
        user.setRole(UserRole.HOTEL_ADMIN);
        user.setHotel(hotel);

        userRepository.save(user);

        // 3. Create invitation token
        String token = UUID.randomUUID().toString();

        UserInvitation invitation = new UserInvitation();
        invitation.setEmail(app.getEmail());
        invitation.setToken(token);
        invitation.setHotelId(hotel.getId());
        invitation.setRole(UserRole.HOTEL_ADMIN.name());
        invitation.setExpiryDate(LocalDateTime.now().plusDays(2));

        invitationRepository.save(invitation);

        // TODO: send email with link
        // https://your-ui.com/set-password?token=xxx
    }

    @Override
    public void reject(Long id) {
        PartnerApplication app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        app.setStatus(PartnerApplicationStatus.REJECTED);
        applicationRepository.save(app);
    }
}