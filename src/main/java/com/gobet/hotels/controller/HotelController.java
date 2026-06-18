package com.gobet.hotels.controller;

import com.gobet.hotels.dto.hotel.*;
import com.gobet.hotels.service.HotelService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Hotel Controller", description = "Hotel APIs")
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
    public List<HotelResponse> getAll(@RequestParam(required = false) String city) {
        return hotelService.getAll(city);
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