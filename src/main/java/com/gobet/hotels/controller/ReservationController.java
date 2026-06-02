package com.gobet.hotelhub.controller;

import com.gobet.hotelhub.dto.reservation.CreateReservationRequest;
import com.gobet.hotelhub.dto.reservation.ReservationResponse;
import com.gobet.hotelhub.service.IReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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