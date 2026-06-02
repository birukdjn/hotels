package com.gobet.hotels.service;

import com.gobet.hotels.dto.guest.CreateGuestRequest;
import com.gobet.hotels.dto.guest.GuestResponse;

import java.util.List;

public interface IGuestService {

    GuestResponse create(Long hotelId,
                         CreateGuestRequest request);

    GuestResponse getById(Long id);

    List<GuestResponse> getByHotel(Long hotelId);

    void delete(Long id);
}
