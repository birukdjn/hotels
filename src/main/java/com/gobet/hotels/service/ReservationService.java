package com.gobet.hotels.service;

import com.gobet.hotels.dto.reservation.CreateReservationRequest;
import com.gobet.hotels.dto.reservation.ReservationResponse;
import com.gobet.hotels.entity.*;
import com.gobet.hotels.enums.ReservationStatus;
import com.gobet.hotels.enums.RoomStatus;
import com.gobet.hotels.exception.NotFoundException;
import com.gobet.hotels.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService implements IReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final HotelRepository hotelRepository;

    @Override
    public ReservationResponse create(Long hotelId, CreateReservationRequest request) {

        if (request.getCheckInDate().isAfter(request.getCheckOutDate())) {
            throw new RuntimeException("Check-in date must be before check-out date");
        }

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new NotFoundException("Hotel not found"));

        Guest guest = guestRepository.findById(request.getGuestId())
                .orElseThrow(() -> new NotFoundException("Guest not found"));

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new NotFoundException("Room not found"));

        // 🚨 CHECK ROOM AVAILABILITY (IMPORTANT LOGIC)
        boolean isBooked = !reservationRepository
                .findByRoomIdAndCheckOutDateAfterAndCheckInDateBefore(
                        room.getId(),
                        request.getCheckInDate(),
                        request.getCheckOutDate()
                ).isEmpty();

        if (isBooked) {
            throw new RuntimeException("Room is already booked for selected dates");
        }

        // 💰 PRICE CALCULATION
        long days = ChronoUnit.DAYS.between(request.getCheckInDate(), request.getCheckOutDate());

        BigDecimal total = room.getPricePerNight()
                .multiply(BigDecimal.valueOf(days));

        // 🏨 CREATE RESERVATION
        Reservation reservation = new Reservation();
        reservation.setHotel(hotel);
        reservation.setGuest(guest);
        reservation.setRoom(room);
        reservation.setCheckInDate(request.getCheckInDate());
        reservation.setCheckOutDate(request.getCheckOutDate());
        reservation.setTotalAmount(total);
        reservation.setStatus(ReservationStatus.PENDING);

        // 🔄 UPDATE ROOM STATUS
        room.setStatus(RoomStatus.RESERVED);
        roomRepository.save(room);

        return map(reservationRepository.save(reservation));
    }

    @Override
    public ReservationResponse getById(Long id) {

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reservation not found"));

        return map(reservation);
    }

    @Override
    public List<ReservationResponse> getByHotel(Long hotelId) {

        return reservationRepository.findAll()
                .stream()
                .filter(r -> r.getHotel().getId().equals(hotelId))
                .map(this::map)
                .collect(Collectors.toList());
    }

    @Override
    public void cancel(Long id) {

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reservation not found"));

        reservation.setStatus(ReservationStatus.CANCELLED);

        Room room = reservation.getRoom();
        room.setStatus(RoomStatus.AVAILABLE);

        roomRepository.save(room);
        reservationRepository.save(reservation);
    }

    private ReservationResponse map(Reservation r) {

        ReservationResponse res = new ReservationResponse();
        res.setId(r.getId());
        res.setHotelId(r.getHotel().getId());
        res.setGuestId(r.getGuest().getId());
        res.setRoomId(r.getRoom().getId());
        res.setCheckInDate(r.getCheckInDate());
        res.setCheckOutDate(r.getCheckOutDate());
        res.setTotalAmount(r.getTotalAmount());
        res.setStatus(r.getStatus().name());

        return res;
    }
}