package com.gobet.hotels.service;

import com.gobet.hotels.dto.room.*;
import com.gobet.hotels.entity.Hotel;
import com.gobet.hotels.entity.Room;
import com.gobet.hotels.enums.RoomStatus;
import com.gobet.hotels.exception.NotFoundException;
import com.gobet.hotels.repository.HotelRepository;
import com.gobet.hotels.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService implements IRoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    @Override
    public RoomResponse create(Long hotelId, CreateRoomRequest request) {

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new NotFoundException("Hotel not found"));

        Room room = new Room();
        room.setHotel(hotel);
        room.setRoomNumber(request.getRoomNumber());
        room.setCapacity(request.getCapacity());
        room.setPricePerNight(request.getPricePerNight());
        room.setStatus(RoomStatus.AVAILABLE);

        return map(roomRepository.save(room));
    }

    @Override
    public RoomResponse getById(Long id) {

        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Room not found"));

        return map(room);
    }

    @Override
    public List<RoomResponse> getByHotel(Long hotelId) {
        return roomRepository.findByHotelIdAndDeletedFalse(hotelId)
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    @Override
    public RoomResponse update(Long id, UpdateRoomRequest request) {

        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Room not found"));

        if (request.getRoomNumber() != null)
            room.setRoomNumber(request.getRoomNumber());

        if (request.getCapacity() != null)
            room.setCapacity(request.getCapacity());

        if (request.getPricePerNight() != null)
            room.setPricePerNight(request.getPricePerNight());

        if (request.getStatus() != null)
            room.setStatus(RoomStatus.valueOf(request.getStatus()));

        return map(roomRepository.save(room));
    }

    @Override
    public void delete(Long id) {

        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Room not found"));

        room.setDeleted(true);
        roomRepository.save(room);
    }

    private RoomResponse map(Room room) {

        RoomResponse res = new RoomResponse();
        res.setId(room.getId());
        res.setRoomNumber(room.getRoomNumber());
        res.setCapacity(room.getCapacity());
        res.setPricePerNight(room.getPricePerNight());
        res.setStatus(room.getStatus().name());
        res.setHotelId(room.getHotel().getId());

        return res;
    }
}