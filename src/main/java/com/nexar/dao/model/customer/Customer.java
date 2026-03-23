package com.nexar.dao.model.customer;

import com.nexar.dao.model.AbstractEntity;
import com.nexar.dao.model.rental.Rental;
import com.nexar.dao.model.user.NexarUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Customer extends AbstractEntity {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private NexarUser user;

    @Column(name = "driver_license_no")
    private String driverLicenseNo;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    private String address;

    private String city;

    private boolean blacklisted;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Rental> rentals = new ArrayList<>();
}
