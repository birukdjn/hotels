package com.gobet.hotelhub.controller;

import com.gobet.hotelhub.dto.room.*;
import com.gobet.hotelhub.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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