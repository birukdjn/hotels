package com.gobet.hotels.repository;

import com.gobet.hotels.entity.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Long> {
    List<Zone> findByDeletedFalse();
    List<Zone> findByRegionIdAndDeletedFalse(Long regionId);
}
