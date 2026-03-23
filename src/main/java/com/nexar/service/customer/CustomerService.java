package com.nexar.service.customer;

import com.nexar.controller.customer.dto.CreateCustomerDto;
import com.nexar.controller.customer.dto.CustomerDto;
import com.nexar.dao.model.customer.Customer;
import com.nexar.dao.model.user.NexarUser;
import com.nexar.dao.repository.customer.CustomerRepository;
import com.nexar.dao.repository.user.NexarUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.nexar.controller.customer.dto.CustomerDto.map;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final NexarUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public CustomerDto register(CreateCustomerDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalStateException("Email already in use: " + dto.getEmail());
        }

        NexarUser user = new NexarUser();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setNexarRole(NexarUser.NexarRole.REGULAR_CUSTOMER);
        userRepository.save(user);

        Customer customer = new Customer();
        customer.setUser(user);
        customer.setDriverLicenseNo(dto.getDriverLicenseNo());
        customer.setDateOfBirth(dto.getDateOfBirth());
        customer.setAddress(dto.getAddress());
        customer.setCity(dto.getCity());
        customer = customerRepository.save(customer);

        return map(customer);
    }
}