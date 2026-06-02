package com.gobet.hotels.entity.common;

import com.gobet.hotels.entity.Hotel;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class HotelAwareEntity extends BaseEntity {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = true)
    private Hotel hotel;
}