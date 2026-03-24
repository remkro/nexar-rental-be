package com.nexar.controller.rental;

import com.nexar.controller.rental.dto.CreateRentalDto;
import com.nexar.controller.rental.dto.RentalDto;
import com.nexar.service.rental.RentalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rental")
@RequiredArgsConstructor
@Slf4j
public class RentalController {
    private final RentalService rentalService;

    @PostMapping("/car")
    public ResponseEntity<RentalDto> createRental(@Valid @RequestBody CreateRentalDto dto) {
        log.info("Create rental request, car: {}, from: {}, to: {}",
                dto.getVehicleId(), dto.getPickupDate(), dto.getReturnDate());

        return ResponseEntity.status(HttpStatus.CREATED).body(rentalService.createCarRental(dto));
    }
}
