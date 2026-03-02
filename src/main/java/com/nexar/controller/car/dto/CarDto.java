package com.nexar.controller.car.dto;

import com.nexar.dao.model.Vehicle;
import com.nexar.dao.model.car.Car;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class CarDto {
    private UUID id;
    private String make;
    private String model;
    private int year;
    private int mileage;
    private int seats;
    private int horsePower;
    private String featuredImage;
    private BigDecimal dailyRate;
    private Car.Body body;
    private Vehicle.Fuel fuel;
    private Vehicle.Transmission transmission;
    private Vehicle.Status status;

    public static CarDto map(Car car) {
        if (car == null) {
            return null;
        }

        return CarDto.builder()
                .id(car.getId())
                .make(car.getMake())
                .model(car.getModel())
                .year(car.getYear())
                .mileage(car.getMileage())
                .seats(car.getSeats())
                .horsePower(car.getHorsePower())
                .featuredImage(car.getFeaturedImage())
                .dailyRate(car.getDailyRate())
                .body(car.getBody())
                .fuel(car.getFuel())
                .transmission(car.getTransmission())
                .status(car.getStatus())
                .build();
    }
}
