package com.gobet.hotelhub.service;

import com.gobet.hotelhub.dto.guest.CreateGuestRequest;
import com.gobet.hotelhub.dto.guest.GuestResponse;

import java.util.List;

public interface IGuestService {

    GuestResponse create(Long hotelId,
                         CreateGuestRequest request);

    GuestResponse getById(Long id);

    List<GuestResponse> getByHotel(Long hotelId);

    void delete(Long id);
}
