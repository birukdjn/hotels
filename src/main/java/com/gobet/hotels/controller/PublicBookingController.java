package com.gobet.hotels.controller;

import com.gobet.hotels.dto.booking.CreatePublicBookingRequest;
import com.gobet.hotels.dto.booking.PublicBookingResponse;
import com.gobet.hotels.service.PublicBookingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Public Booking Controller", description = "Public booking APIs for guests")
@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class PublicBookingController {

    private final PublicBookingService publicBookingService;

    @PostMapping("/public")
    public PublicBookingResponse createBooking(@Valid @RequestBody CreatePublicBookingRequest request) {
        return publicBookingService.createBooking(request);
    }
}
