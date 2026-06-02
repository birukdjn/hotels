package com.gobet.hotelhub.repository;

import com.gobet.hotelhub.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}