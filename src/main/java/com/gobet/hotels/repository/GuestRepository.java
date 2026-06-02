package com.gobet.hotels.repository;

import com.gobet.hotels.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuestRepository extends JpaRepository<Guest, Long> {

    List<Guest> findByHotelIdAndDeletedFalse(Long hotelId);

}