package com.gobet.hotels.dto.booking;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreatePublicBookingRequest {

    @NotNull(message = "Hotel ID is required")
    private Long hotelId;

    @NotNull(message = "Room ID is required")
    private Long roomId;

    @NotNull(message = "Check-in date is required")
    private LocalDate checkInDate;

    @NotNull(message = "Check-out date is required")
    private LocalDate checkOutDate;

    @NotBlank(message = "Guest full name is required")
    private String guestFullName;

    @NotBlank(message = "Guest email is required")
    @Email(message = "Invalid email format")
    private String guestEmail;

    private String guestPhone;
    private String guestNationality;
    private String guestNationalId;

    @NotBlank(message = "Payment method is required")
    private String paymentMethod; // e.g. MOBILE_MONEY, CARD, BANK_TRANSFER, CASH

    @NotBlank(message = "Transaction reference is required")
    private String transactionReference;
}
