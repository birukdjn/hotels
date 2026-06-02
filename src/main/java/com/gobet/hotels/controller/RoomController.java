package com.gobet.hotels.controller;

import com.gobet.hotels.dto.room.*;
import com.gobet.hotels.service.RoomService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Room Controller", description = "Room APIs")
@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/{hotelId}")
    public RoomResponse create(
            @PathVariable Long hotelId,
            @Valid @RequestBody CreateRoomRequest request) {
        return roomService.create(hotelId, request);
    }

    @GetMapping("/{id}")
    public RoomResponse getById(@PathVariable Long id) {
        return roomService.getById(id);
    }

    @GetMapping("/hotel/{hotelId}")
    public List<RoomResponse> getByHotel(@PathVariable Long hotelId) {
        return roomService.getByHotel(hotelId);
    }

    @PutMapping("/{id}")
    public RoomResponse update(
            @PathVariable Long id,
            @RequestBody UpdateRoomRequest request) {
        return roomService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        roomService.delete(id);
        return "Room deleted successfully";
    }
}