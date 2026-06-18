package com.gobet.hotels.entity;
import com.gobet.hotels.entity.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "hotels")
public class Hotel extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    private String phone;

    private String email;

    private String address;

    private String city;

    private String logoUrl;

    @Column(nullable = false)
    private Boolean active = true;

    // Getters and Setters
}