package com.gobet.hotels.dto.metadata;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityResponse {
    private Long id;
    private String name;
    private Long zoneId;
    private String zoneName;
}
