package com.gobet.hotels.dto.metadata;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ZoneResponse {
    private Long id;
    private String name;
    private Long regionId;
    private String regionName;
}
