package com.nexar.dao.repository.customer;

import com.nexar.dao.model.customer.Customer;
import com.nexar.dao.repository.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends AbstractRepository<Customer> {
    @Query("SELECT c FROM Customer c JOIN c.user u WHERE LOWER(u.email) = LOWER(:email)")
    Optional<Customer> findByUserEmailIgnoreCase(@Param("email") String email);
}
