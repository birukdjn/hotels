package com.gobet.hotels.repository;

import com.gobet.hotels.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GuestRepository extends JpaRepository<Guest, Long> {

    List<Guest> findByHotelIdAndDeletedFalse(Long hotelId);

    Optional<Guest> findByEmailAndHotelIdAndDeletedFalse(String email, Long hotelId);

}