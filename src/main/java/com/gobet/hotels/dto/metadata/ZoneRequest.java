package com.gobet.hotels.dto.metadata;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ZoneRequest {

    @NotBlank(message = "Zone name is required")
    private String name;

    @NotNull(message = "Region ID is required")
    private Long regionId;
}
