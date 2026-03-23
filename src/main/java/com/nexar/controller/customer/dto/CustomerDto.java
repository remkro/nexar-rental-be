package com.nexar.controller.customer.dto;

import com.nexar.dao.model.customer.Customer;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;


@Builder
@Data
public class CustomerDto {
    private String firstName;
    private String lastName;
    private String email;
    private String driverLicenseNo;
    private LocalDate dateOfBirth;
    private String address;
    private String city;
    private Date creationTimestamp;

    public static CustomerDto map(Customer customer) {
        if (customer == null) {
            return null;
        }

        return CustomerDto.builder()
                .firstName(customer.getUser().getFirstName())
                .lastName(customer.getUser().getLastName())
                .email(customer.getUser().getEmail())
                .dateOfBirth(customer.getDateOfBirth())
                .driverLicenseNo(customer.getDriverLicenseNo())
                .address(customer.getAddress())
                .city(customer.getCity())
                .creationTimestamp(customer.getCreationTimestamp())
                .build();
    }
}
