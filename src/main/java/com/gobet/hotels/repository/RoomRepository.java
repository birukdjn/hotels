package com.gobet.hotels.repository;

import com.gobet.hotels.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByHotelIdAndDeletedFalse(Long hotelId);
}