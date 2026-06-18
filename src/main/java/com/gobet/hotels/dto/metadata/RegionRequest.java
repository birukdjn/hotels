package com.gobet.hotels.dto.metadata;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegionRequest {

    @NotBlank(message = "Region name is required")
    private String name;
}
