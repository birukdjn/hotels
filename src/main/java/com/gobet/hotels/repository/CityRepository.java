package com.gobet.hotels.repository;

import com.gobet.hotels.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findByDeletedFalse();
    List<City> findByZoneIdAndDeletedFalse(Long zoneId);
}
