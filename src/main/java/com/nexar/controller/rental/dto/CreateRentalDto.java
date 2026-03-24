package com.nexar.controller.rental.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateRentalDto {
    @NotBlank
    String vehicleId;

    @NotNull
    @FutureOrPresent
    LocalDate pickupDate;

    @NotNull
    LocalDate returnDate;

    @NotBlank
    String pickupLocation;

    @NotBlank
    String returnLocation;

    String notes;
}
