package com.gobet.hotelhub.service;

import com.gobet.hotelhub.dto.hotel.*;
import com.gobet.hotelhub.entity.Hotel;
import com.gobet.hotelhub.exception.NotFoundException;
import com.gobet.hotelhub.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelService implements IHotelService {

    private final HotelRepository hotelRepository;

    @Override
    public HotelResponse create(CreateHotelRequest request) {

        Hotel hotel = new Hotel();
        hotel.setName(request.getName());
        hotel.setEmail(request.getEmail());
        hotel.setPhone(request.getPhone());
        hotel.setAddress(request.getAddress());
        hotel.setLogoUrl(request.getLogoUrl());
        hotel.setActive(true);

        return map(hotelRepository.save(hotel));
    }

    @Override
    public HotelResponse getById(Long id) {

        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Hotel not found with id: " + id));

        return map(hotel);
    }

    @Override
    public List<HotelResponse> getAll() {
        return hotelRepository.findAll()
                .stream()
                .filter(h -> Boolean.FALSE.equals(h.getDeleted()))
                .map(this::map)
                .collect(Collectors.toList());
    }

    @Override
    public HotelResponse update(Long id, UpdateHotelRequest request) {

        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Hotel not found with id: " + id));

        hotel.setName(request.getName());
        hotel.setEmail(request.getEmail());
        hotel.setPhone(request.getPhone());
        hotel.setAddress(request.getAddress());
        hotel.setLogoUrl(request.getLogoUrl());
        hotel.setActive(request.getActive());

        return map(hotelRepository.save(hotel));
    }

    @Override
    public void delete(Long id) {

        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Hotel not found with id: " + id));

        // Soft delete
        hotel.setDeleted(true);
        hotelRepository.save(hotel);
    }

    private HotelResponse map(Hotel hotel) {
        HotelResponse res = new HotelResponse();
        res.setId(hotel.getId());
        res.setName(hotel.getName());
        res.setEmail(hotel.getEmail());
        res.setPhone(hotel.getPhone());
        res.setAddress(hotel.getAddress());
        res.setLogoUrl(hotel.getLogoUrl());
        res.setActive(hotel.getActive());
        return res;
    }
}