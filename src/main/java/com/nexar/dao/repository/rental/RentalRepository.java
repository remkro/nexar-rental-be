package com.nexar.dao.repository.rental;

import com.nexar.dao.model.rental.Rental;
import com.nexar.dao.repository.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RentalRepository extends AbstractRepository<Rental> {
    @Query("""
        SELECT COUNT(r) FROM Rental r
        WHERE r.customer.id = :customerId
        AND r.status IN (Rental.RentalStatus.PENDING,Rental.RentalStatus.CONFIRMED, Rental.RentalStatus.ACTIVE)
    """)
    long countActiveRentalsByCustomer(@Param("customerId") UUID customerId);

    List<Rental> findByCarIdAndStatusNotIn(UUID carId, List<Rental.RentalStatus> statuses);
}
