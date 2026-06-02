package com.gobet.hotelhub.repository;

import com.gobet.hotelhub.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByRoomIdAndDeletedFalse(Long roomId);

    List<Reservation> findByRoomIdAndCheckOutDateAfterAndCheckInDateBefore(
            Long roomId,
            LocalDate checkIn,
            LocalDate checkOut
    );
}