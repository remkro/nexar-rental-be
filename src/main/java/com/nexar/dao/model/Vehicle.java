package com.nexar.dao.model;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class Vehicle extends AbstractEntity {
    @Column(name = "license_plate")
    private String licensePlate;

    private String make;

    private String model;

    private int year;

    private int mileage;

    private int seats;

    @Column(name = "horse_power")
    private int horsePower;

    @Column(name = "featured_image")
    private String featuredImage;

    @Column(name = "daily_rate")
    private BigDecimal dailyRate;

    @Enumerated(EnumType.STRING)
    private Fuel fuel;

    @Enumerated(EnumType.STRING)
    private Transmission transmission;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Fuel {
        GASOLINE,
        DIESEL,
        ELECTRIC,
        HYBRID
    }

    public enum Transmission {
        MANUAL,
        AUTOMATIC
    }

    public enum Status {
        AVAILABLE,
        RENTED,
        MAINTENANCE,
        RESERVED,
        RETIRED
    }

    public boolean isAvailable() {
        return status == Status.AVAILABLE;
    }
}
