package com.gobet.hotels.dto.reservation;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ReservationResponse {

    private Long id;
    private Long hotelId;
    private Long guestId;
    private Long roomId;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    private BigDecimal totalAmount;

    private String status;
}