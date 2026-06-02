package com.gobet.hotelhub.controller;

import com.gobet.hotelhub.dto.guest.*;
import com.gobet.hotelhub.service.GuestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guests")
@RequiredArgsConstructor
public class GuestController {

    private final GuestService guestService;

    @PostMapping("/{hotelId}")
    public GuestResponse create(
            @PathVariable Long hotelId,
            @Valid @RequestBody CreateGuestRequest request) {

        return guestService.create(hotelId, request);
    }

    @GetMapping("/{id}")
    public GuestResponse getById(@PathVariable Long id) {
        return guestService.getById(id);
    }

    @GetMapping("/hotel/{hotelId}")
    public List<GuestResponse> getByHotel(
            @PathVariable Long hotelId) {

        return guestService.getByHotel(hotelId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        guestService.delete(id);
    }
}