package com.gobet.hotelhub.controller;

import com.gobet.hotelhub.dto.hotel.*;
import com.gobet.hotelhub.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @PostMapping
    public HotelResponse create(@Valid @RequestBody CreateHotelRequest request) {
        return hotelService.create(request);
    }

    @GetMapping("/{id}")
    public HotelResponse getById(@PathVariable Long id) {
        return hotelService.getById(id);
    }

    @GetMapping
    public List<HotelResponse> getAll() {
        return hotelService.getAll();
    }

    @PutMapping("/{id}")
    public HotelResponse update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateHotelRequest request) {
        return hotelService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        hotelService.delete(id);
        return "Hotel deleted successfully";
    }
}