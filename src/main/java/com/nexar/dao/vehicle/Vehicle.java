package com.nexar.dao.vehicle;

import com.nexar.dao.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

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

    @Column(name = "daily_rate")
    private BigDecimal dailyRate;

    @Column(name = "fuel_type")
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum FuelType {
        GASOLINE,
        DIESEL,
        ELECTRIC,
        HYBRID
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
