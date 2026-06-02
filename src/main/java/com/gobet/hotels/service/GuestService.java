package com.gobet.hotelhub.service;

import com.gobet.hotelhub.dto.guest.*;
import com.gobet.hotelhub.entity.Guest;
import com.gobet.hotelhub.entity.Hotel;
import com.gobet.hotelhub.exception.NotFoundException;
import com.gobet.hotelhub.repository.GuestRepository;
import com.gobet.hotelhub.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GuestService implements IGuestService {

    private final GuestRepository guestRepository;
    private final HotelRepository hotelRepository;

    @Override
    public GuestResponse create(Long hotelId,
                                CreateGuestRequest request) {

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() ->
                        new NotFoundException("Hotel not found"));

        Guest guest = new Guest();

        guest.setHotel(hotel);
        guest.setFullName(request.getFullName());
        guest.setPhone(request.getPhone());
        guest.setEmail(request.getEmail());
        guest.setNationality(request.getNationality());
        guest.setNationalId(request.getNationalId());

        return map(guestRepository.save(guest));
    }

    @Override
    public GuestResponse getById(Long id) {

        Guest guest = guestRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Guest not found"));

        return map(guest);
    }

    @Override
    public List<GuestResponse> getByHotel(Long hotelId) {

        return guestRepository
                .findByHotelIdAndDeletedFalse(hotelId)
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public void delete(Long id) {

        Guest guest = guestRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Guest not found"));

        guest.setDeleted(true);

        guestRepository.save(guest);
    }

    private GuestResponse map(Guest guest) {

        GuestResponse response = new GuestResponse();

        response.setId(guest.getId());
        response.setHotelId(guest.getHotel().getId());
        response.setFullName(guest.getFullName());
        response.setPhone(guest.getPhone());
        response.setEmail(guest.getEmail());
        response.setNationality(guest.getNationality());
        response.setNationalId(guest.getNationalId());

        return response;
    }
}

