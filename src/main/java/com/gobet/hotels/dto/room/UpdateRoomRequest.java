package com.gobet.hotelhub.dto.room;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateRoomRequest {

    private String roomNumber;
    private Integer capacity;
    private BigDecimal pricePerNight;
    private String status;
}