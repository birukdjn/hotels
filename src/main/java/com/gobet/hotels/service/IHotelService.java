package com.gobet.hotels.service;

import com.gobet.hotels.dto.hotel.*;

import java.util.List;

public interface IHotelService {

    HotelResponse create(CreateHotelRequest request);

    HotelResponse getById(Long id);

    List<HotelResponse> getAll();

    HotelResponse update(Long id, UpdateHotelRequest request);

    void delete(Long id);
}