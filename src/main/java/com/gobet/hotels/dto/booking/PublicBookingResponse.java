package com.gobet.hotels.dto.booking;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PublicBookingResponse {
    private Long reservationId;
    private String reservationStatus;
    private BigDecimal totalAmount;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String guestName;
    private String roomNumber;
    private Long paymentId;
    private String paymentMethod;
    private String transactionReference;
}
