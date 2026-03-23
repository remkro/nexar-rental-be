package com.nexar.dao.repository.customer;

import com.nexar.dao.model.customer.Customer;
import com.nexar.dao.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends AbstractRepository<Customer> {
}
