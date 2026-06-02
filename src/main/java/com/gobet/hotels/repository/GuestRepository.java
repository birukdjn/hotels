package com.gobet.hotelhub.repository;

import com.gobet.hotelhub.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuestRepository extends JpaRepository<Guest, Long> {

    List<Guest> findByHotelIdAndDeletedFalse(Long hotelId);

}