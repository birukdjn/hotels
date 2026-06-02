package com.gobet.hotels.service;

import com.gobet.hotels.dto.reservation.CreateReservationRequest;
import com.gobet.hotels.dto.reservation.ReservationResponse;

import java.util.List;

public interface IReservationService {

    ReservationResponse create(Long hotelId, CreateReservationRequest request);

    ReservationResponse getById(Long id);

    List<ReservationResponse> getByHotel(Long hotelId);

    void cancel(Long id);
}