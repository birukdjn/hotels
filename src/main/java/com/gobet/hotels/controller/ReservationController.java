package com.gobet.hotels.controller;

import com.gobet.hotels.dto.reservation.CreateReservationRequest;
import com.gobet.hotels.dto.reservation.ReservationResponse;
import com.gobet.hotels.service.IReservationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Reservation Controller", description = "Reservation APIs")
@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final IReservationService reservationService;

    @PostMapping("/{hotelId}")
    public ReservationResponse create(
            @PathVariable Long hotelId,
            @Valid @RequestBody CreateReservationRequest request) {
        return reservationService.create(hotelId, request);
    }

    @GetMapping("/{id}")
    public ReservationResponse getById(@PathVariable Long id) {
        return reservationService.getById(id);
    }

    @GetMapping("/hotel/{hotelId}")
    public List<ReservationResponse> getByHotel(@PathVariable Long hotelId) {
        return reservationService.getByHotel(hotelId);
    }

    @DeleteMapping("/{id}")
    public String cancel(@PathVariable Long id) {
        reservationService.cancel(id);
        return "Reservation cancelled successfully";
    }
}