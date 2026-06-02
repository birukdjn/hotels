package com.gobet.hotelhub.repository;

import com.gobet.hotelhub.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByHotelIdAndDeletedFalse(Long hotelId);
}