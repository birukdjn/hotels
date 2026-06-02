package com.gobet.hotels.entity;
import com.gobet.hotels.entity.common.HotelAwareEntity;
import com.gobet.hotels.enums.RoomStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
        name = "rooms",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"hotel_id", "room_number"})
        }
)
public class Room extends HotelAwareEntity {

    @Column(name = "room_number", nullable = false)
    private String roomNumber;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal pricePerNight;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomStatus status = RoomStatus.AVAILABLE;

    // Getters and Setters
}