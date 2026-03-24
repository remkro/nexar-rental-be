package com.nexar.service.rental;

import com.nexar.controller.rental.dto.CreateRentalDto;
import com.nexar.controller.rental.dto.RentalDto;
import com.nexar.dao.model.car.Car;
import com.nexar.dao.model.customer.Customer;
import com.nexar.dao.model.rental.Rental;
import com.nexar.dao.repository.car.CarRepository;
import com.nexar.dao.repository.customer.CustomerRepository;
import com.nexar.dao.repository.rental.RentalRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static com.nexar.controller.rental.dto.RentalDto.map;

@Service
@RequiredArgsConstructor
@Slf4j
public class RentalService {
    private final RentalRepository rentalRepository;
    private final CustomerRepository customerRepository;
    private final CarRepository carRepository;

    @Transactional
    public RentalDto createCarRental(CreateRentalDto dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Customer customer = customerRepository.findByUserEmailIgnoreCase(email)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        Car car = carRepository.findByIdWithLock(UUID.fromString(dto.getVehicleId()))
                .orElseThrow(() -> new EntityNotFoundException("Car not found"));


        // TODO: implement
//        validateRentalCreation(customer, car, dto.getPickupDate(), dto.getReturnDate());

        int days = (int) ChronoUnit.DAYS.between(dto.getPickupDate(), dto.getReturnDate());
        BigDecimal baseAmount = car.getDailyRate().multiply(BigDecimal.valueOf(days));

        // TODO: implement
//        BigDecimal discount = calculateDiscount(customer, days, baseAmount);
        BigDecimal discount = BigDecimal.ZERO;
        BigDecimal total = baseAmount.subtract(discount).setScale(2, RoundingMode.HALF_UP);

        Rental rental = rentalRepository.save(Rental.builder()
                .customer(customer)
                .car(car)
                .pickupDate(dto.getPickupDate())
                .returnDate(dto.getReturnDate())
                .pickupLocation(dto.getPickupLocation())
                .returnLocation(dto.getReturnLocation())
                .status(Rental.RentalStatus.PENDING)
                .dailyRate(car.getDailyRate())
                .totalDays(days)
                .baseAmount(baseAmount)
                .discountAmount(discount)
                .extraCharges(BigDecimal.ZERO)
                .totalAmount(total)
                .notes(dto.getNotes())
                .build());

        log.info("Rental created, customer: {}, car: {}",
                customer.getUser().getEmail(), car.getMake() + " " + car.getModel());

        return map(rental);
    }


    //TODO: implement
//    private void validateRentalCreation(Customer customer, Car car, LocalDate pickup, LocalDate returnDate) {
//        if (customer.isBlacklisted()) {
//            throw new BusinessException("Account is suspended.");
//        }
//        if (!car.isAvailable()) {
//            throw new BusinessException("Car is not available for rental");
//        }
//        if (returnDate.isBefore(pickup) || returnDate.isEqual(pickup)) {
//            throw new BusinessException("Return date must be after pickup date");
//        }
//        if (pickup.isBefore(LocalDate.now())) {
//            throw new BusinessException("Pickup date cannot be in the past");
//        }
//
//        long active = rentalRepository.countActiveRentalsByCustomer(customer.getId());
//        if (active >= maxActiveRentals) {
//            throw new BusinessException("Maximum active rentals (" + maxActiveRentals + ") reached");
//        }
//
//        List<Rental> conflicts = rentalRepository.findByCarIdAndStatusNotIn(
//                car.getId(),
//                List.of(Rental.RentalStatus.CANCELLED, Rental.RentalStatus.COMPLETED, RentalStatus.NO_SHOW)
//        );
//
//        boolean hasConflict = conflicts
//                .stream()
//                .anyMatch(rental ->
//                rental.getPickupDate().isBefore(returnDate) && rental.getReturnDate().isAfter(pickup));
//
//        if (hasConflict) {
//            throw new BusinessException("Car is already booked for the selected period");
//        }
//    }
}
