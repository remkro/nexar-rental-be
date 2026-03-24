package com.nexar.controller.rental.dto;

import com.nexar.dao.model.rental.Rental;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class RentalDto {
    private UUID id;

    private String customerFirstName;
    private String customerLastName;
    private String customerEmail;

    private String carMake;
    private String carModel;
    private String carLicensePlate;

    private LocalDate pickupDate;
    private LocalDate returnDate;
    private LocalDate actualReturnDate;

    private String pickupLocation;
    private String returnLocation;

    private Rental.RentalStatus status;
    private boolean active;

    private BigDecimal dailyRate;
    private int totalDays;
    private BigDecimal baseAmount;
    private BigDecimal discountAmount;
    private BigDecimal extraCharges;
    private BigDecimal totalAmount;

    private String notes;

    public static RentalDto map(Rental rental) {
        return RentalDto.builder()
                .id(rental.getId())
                .customerFirstName(rental.getCustomer().getUser().getFirstName())
                .customerLastName(rental.getCustomer().getUser().getLastName())
                .customerEmail(rental.getCustomer().getUser().getEmail())
                .carMake(rental.getCar().getMake())
                .carModel(rental.getCar().getModel())
                .carLicensePlate(rental.getCar().getLicensePlate())
                .pickupDate(rental.getPickupDate())
                .returnDate(rental.getReturnDate())
                .actualReturnDate(rental.getActualReturnDate())
                .pickupLocation(rental.getPickupLocation())
                .returnLocation(rental.getReturnLocation())
                .status(rental.getStatus())
                .active(rental.isActive())
                .dailyRate(rental.getDailyRate())
                .totalDays(rental.getTotalDays())
                .baseAmount(rental.getBaseAmount())
                .discountAmount(rental.getDiscountAmount())
                .extraCharges(rental.getExtraCharges())
                .totalAmount(rental.getTotalAmount())
                .notes(rental.getNotes())
                .build();
    }
}
