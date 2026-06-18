package com.gobet.hotels.dto.metadata;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityRequest {

    @NotBlank(message = "City name is required")
    private String name;

    @NotNull(message = "Zone ID is required")
    private Long zoneId;
}
