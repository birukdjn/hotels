package com.gobet.hotels.repository;

import com.gobet.hotels.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    List<Hotel> findByDeletedFalse();

    List<Hotel> findByCityIgnoreCaseAndDeletedFalse(String city);
}