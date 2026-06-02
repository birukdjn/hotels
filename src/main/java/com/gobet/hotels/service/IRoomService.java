package com.gobet.hotels.service;

import com.gobet.hotels.dto.room.*;

import java.util.List;

public interface IRoomService {

    RoomResponse create(Long hotelId, CreateRoomRequest request);

    RoomResponse getById(Long id);

    List<RoomResponse> getByHotel(Long hotelId);

    RoomResponse update(Long id, UpdateRoomRequest request);

    void delete(Long id);
}