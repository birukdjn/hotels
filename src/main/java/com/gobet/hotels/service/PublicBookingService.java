package com.gobet.hotels.service;

import com.gobet.hotels.dto.booking.CreatePublicBookingRequest;
import com.gobet.hotels.dto.booking.PublicBookingResponse;
import com.gobet.hotels.entity.*;
import com.gobet.hotels.enums.PaymentMethod;
import com.gobet.hotels.enums.ReservationStatus;
import com.gobet.hotels.enums.RoomStatus;
import com.gobet.hotels.exception.NotFoundException;
import com.gobet.hotels.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PublicBookingService {

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    public PublicBookingResponse createBooking(CreatePublicBookingRequest request) {
        // 1. Validate dates
        if (request.getCheckInDate().isAfter(request.getCheckOutDate()) || request.getCheckInDate().equals(request.getCheckOutDate())) {
            throw new IllegalArgumentException("Check-in date must be before check-out date");
        }

        // 2. Retrieve Hotel and Room
        Hotel hotel = hotelRepository.findById(request.getHotelId())
                .orElseThrow(() -> new NotFoundException("Hotel not found with ID: " + request.getHotelId()));

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new NotFoundException("Room not found with ID: " + request.getRoomId()));

        if (!room.getHotel().getId().equals(hotel.getId())) {
            throw new IllegalArgumentException("Selected room does not belong to the selected hotel");
        }

        // 3. Check room availability (overlap reservations)
        boolean isBooked = !reservationRepository
                .findByRoomIdAndCheckOutDateAfterAndCheckInDateBefore(
                        room.getId(),
                        request.getCheckInDate(),
                        request.getCheckOutDate()
                ).isEmpty();

        if (isBooked) {
            throw new IllegalStateException("Room is already booked for selected dates");
        }

        // 4. Find or create Guest (by email and hotel ID)
        Guest guest = guestRepository.findByEmailAndHotelIdAndDeletedFalse(request.getGuestEmail(), hotel.getId())
                .orElseGet(() -> {
                    Guest newGuest = new Guest();
                    newGuest.setHotel(hotel);
                    newGuest.setEmail(request.getGuestEmail());
                    return newGuest;
                });

        // Update fields regardless of if existing or new
        guest.setFullName(request.getGuestFullName());
        guest.setPhone(request.getGuestPhone());
        guest.setNationality(request.getGuestNationality());
        guest.setNationalId(request.getGuestNationalId());
        guest = guestRepository.save(guest);

        // 5. Calculate Price
        long nights = ChronoUnit.DAYS.between(request.getCheckInDate(), request.getCheckOutDate());
        BigDecimal totalAmount = room.getPricePerNight().multiply(BigDecimal.valueOf(nights));

        // 6. Create Reservation
        Reservation reservation = new Reservation();
        reservation.setHotel(hotel);
        reservation.setGuest(guest);
        reservation.setRoom(room);
        reservation.setCheckInDate(request.getCheckInDate());
        reservation.setCheckOutDate(request.getCheckOutDate());
        reservation.setTotalAmount(totalAmount);
        reservation.setStatus(ReservationStatus.CONFIRMED); // Public bookings are pre-paid/confirmed
        reservation = reservationRepository.save(reservation);

        // 7. Create Payment
        Payment payment = new Payment();
        payment.setReservation(reservation);
        payment.setAmount(totalAmount);
        try {
            payment.setPaymentMethod(PaymentMethod.valueOf(request.getPaymentMethod().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid payment method: " + request.getPaymentMethod());
        }
        payment.setTransactionReference(request.getTransactionReference());
        payment = paymentRepository.save(payment);

        // 8. Update Room Status
        room.setStatus(RoomStatus.RESERVED);
        roomRepository.save(room);

        // 9. Build response
        PublicBookingResponse response = new PublicBookingResponse();
        response.setReservationId(reservation.getId());
        response.setReservationStatus(reservation.getStatus().name());
        response.setTotalAmount(totalAmount);
        response.setCheckInDate(reservation.getCheckInDate());
        response.setCheckOutDate(reservation.getCheckOutDate());
        response.setGuestName(guest.getFullName());
        response.setRoomNumber(room.getRoomNumber());
        response.setPaymentId(payment.getId());
        response.setPaymentMethod(payment.getPaymentMethod().name());
        response.setTransactionReference(payment.getTransactionReference());

        return response;
    }
}
