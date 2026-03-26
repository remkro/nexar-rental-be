package com.nexar.service.rental;

import com.nexar.controller.rental.dto.CreateRentalDto;
import com.nexar.controller.rental.dto.RentalDto;
import com.nexar.dao.model.car.Car;
import com.nexar.dao.model.customer.Customer;
import com.nexar.dao.model.rental.Rental;
import com.nexar.dao.repository.car.CarRepository;
import com.nexar.dao.repository.customer.CustomerRepository;
import com.nexar.dao.repository.rental.RentalRepository;
import com.nexar.infrastructure.exception.ResourceConflictException;
import com.nexar.infrastructure.exception.ValidationException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static com.nexar.controller.rental.dto.RentalDto.map;

@Service
@RequiredArgsConstructor
@Slf4j
public class RentalService {
    private final RentalRepository rentalRepository;
    private final CustomerRepository customerRepository;
    private final CarRepository carRepository;

    @Value("${app.rental.max-active-rentals-per-customer}")
    private int maxActiveRentals;

    @Transactional
    public RentalDto createCarRental(CreateRentalDto dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Customer customer = customerRepository.findByUserEmailIgnoreCase(email)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        Car car = carRepository.findByIdWithLock(UUID.fromString(dto.getVehicleId()))
                .orElseThrow(() -> new EntityNotFoundException("Car not found"));

        validateRentalCreation(customer, car, dto.getPickupDate(), dto.getReturnDate());

        Rental rental = buildRental(customer, car, dto);
        rentalRepository.save(rental);

        log.info("Rental created, customer: {}, car: {} {}",
                customer.getUser().getEmail(), car.getMake(), car.getModel());

        return map(rental);
    }

    private Rental buildRental(Customer customer, Car car, CreateRentalDto dto) {
        long days = ChronoUnit.DAYS.between(dto.getPickupDate(), dto.getReturnDate());
        BigDecimal baseAmount = car.getDailyRate().multiply(BigDecimal.valueOf(days));

        // TODO: implement discount calculation
        BigDecimal discount = BigDecimal.ZERO;
        BigDecimal total = baseAmount.subtract(discount).setScale(2, RoundingMode.HALF_UP);

        return Rental.builder()
                .customer(customer)
                .car(car)
                .pickupDate(dto.getPickupDate())
                .returnDate(dto.getReturnDate())
                .pickupLocation(dto.getPickupLocation())
                .returnLocation(dto.getReturnLocation())
                .status(Rental.RentalStatus.PENDING)
                .dailyRate(car.getDailyRate())
                .totalDays((int) days)
                .baseAmount(baseAmount)
                .discountAmount(discount)
                .extraCharges(BigDecimal.ZERO)
                .totalAmount(total)
                .notes(dto.getNotes())
                .build();
    }

    private void validateRentalCreation(Customer customer, Car car, LocalDate pickup, LocalDate returnDate) {
        if (customer.isBlacklisted()) {
            throw new ValidationException("Account is suspended");
        }

        validateDates(pickup, returnDate);

        if (!car.isAvailable()) {
            throw new ResourceConflictException("Car is not available for rental");
        }

        long activeRentals = rentalRepository.countActiveRentalsByCustomer(customer.getId());
        if (activeRentals >= maxActiveRentals) {
            throw new ValidationException("Maximum active rentals (" + maxActiveRentals + ") reached");
        }

        validateNoBookingConflict(car.getId(), pickup, returnDate);
    }

    private void validateDates(LocalDate pickup, LocalDate returnDate) {
        if (pickup.isBefore(LocalDate.now())) {
            throw new ValidationException("Pickup date cannot be in the past");
        }
        if (!returnDate.isAfter(pickup)) {
            throw new ValidationException("Return date must be after pickup date");
        }
    }

    private void validateNoBookingConflict(UUID carId, LocalDate pickup, LocalDate returnDate) {
        var statuses = List.of(
                Rental.RentalStatus.CANCELLED,
                Rental.RentalStatus.COMPLETED,
                Rental.RentalStatus.NO_SHOW
        );

        boolean hasConflict = rentalRepository
                .findByCarIdAndStatusNotIn(carId, statuses)
                .stream()
                .anyMatch(rental -> rental.getPickupDate().isBefore(returnDate) && rental.getReturnDate().isAfter(pickup));

        if (hasConflict) {
            throw new ResourceConflictException("Car is already booked for the selected period");
        }
    }
}