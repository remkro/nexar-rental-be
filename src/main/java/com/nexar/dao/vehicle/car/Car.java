package com.nexar.dao.vehicle.car;

import com.nexar.dao.vehicle.Vehicle;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cars")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Car extends Vehicle {
    @Enumerated(EnumType.STRING)
    @Column(name = "body_type")
    private BodyType bodyType;

    @Enumerated(EnumType.STRING)
    private TransmissionType transmission;

    public enum BodyType {
        SEDAN,
        COUPE,
        HATCHBACK,
        WAGON,
        SUV,
        CONVERTIBLE,
        MINIVAN,
        PICKUP_TRUCK
    }

    public enum TransmissionType {
        MANUAL,
        AUTOMATIC
    }
}