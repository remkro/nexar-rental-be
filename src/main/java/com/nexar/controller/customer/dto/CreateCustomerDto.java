package com.nexar.controller.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateCustomerDto {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String driverLicenseNo;

    @NotNull
    private LocalDate dateOfBirth;

    @NotBlank
    private String address;

    @NotBlank
    private String city;
}