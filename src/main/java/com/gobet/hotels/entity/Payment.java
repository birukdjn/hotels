package com.gobet.hotels.entity;
import com.gobet.hotels.entity.common.BaseEntity;
import com.gobet.hotels.enums.PaymentMethod;
import jakarta.persistence.*;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    private String transactionReference;

    // Getters and Setters
}