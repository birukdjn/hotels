package com.gobet.hotelhub.service;

import com.gobet.hotelhub.dto.reservation.CreateReservationRequest;
import com.gobet.hotelhub.dto.reservation.ReservationResponse;

import java.util.List;

public interface IReservationService {

    ReservationResponse create(Long hotelId, CreateReservationRequest request);

    ReservationResponse getById(Long id);

    List<ReservationResponse> getByHotel(Long hotelId);

    void cancel(Long id);
}