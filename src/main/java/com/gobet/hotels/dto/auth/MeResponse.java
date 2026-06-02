package com.gobet.hotels.dto.auth;

public record MeResponse(
        Long id,
        String email,
        String firstName,
        String lastName,
        String role,
        Long hotelId
) {}
