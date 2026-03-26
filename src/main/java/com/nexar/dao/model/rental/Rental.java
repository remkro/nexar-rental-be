package com.nexar.dao.model.rental;

import com.nexar.dao.model.AbstractEntity;
import com.nexar.dao.model.car.Car;
import com.nexar.dao.model.customer.Customer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "rental")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rental extends AbstractEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;

    @Column(name = "pickup_date")
    private LocalDate pickupDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Column(name = "actual_return_date")
    private LocalDate actualReturnDate;

    @Column(name = "pickup_location")
    private String pickupLocation;

    @Column(name = "return_location")
    private String returnLocation;

    @Enumerated(EnumType.STRING)
    @Column
    private RentalStatus status = RentalStatus.PENDING;

    @Column(name = "daily_rate")
    private BigDecimal dailyRate;

    @Column(name = "total_days")
    private int totalDays;

    @Column(name = "base_amount")
    private BigDecimal baseAmount;

    @Column(name = "discount_amount")
    private BigDecimal discountAmount;

    @Column(name = "extra_charges")
    private BigDecimal extraCharges;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(columnDefinition = "TEXT")
    private String notes;

    public boolean isActive() {
        return status == RentalStatus.ACTIVE || status == RentalStatus.CONFIRMED;
    }

    public boolean isCancellable() {
        return status == RentalStatus.PENDING || status == RentalStatus.CONFIRMED;
    }

    public enum RentalStatus { PENDING, CONFIRMED, ACTIVE, COMPLETED, CANCELLED, NO_SHOW }
}
