package com.nexar.service.customer;

import com.nexar.controller.customer.dto.CreateCustomerDto;
import com.nexar.controller.customer.dto.CustomerDto;
import com.nexar.dao.model.customer.Customer;
import com.nexar.dao.model.user.NexarUser;
import com.nexar.dao.repository.customer.CustomerRepository;
import com.nexar.dao.repository.user.NexarUserRepository;
import com.nexar.service.email.EmailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.nexar.controller.customer.dto.CustomerDto.map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final NexarUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Value("${app.email.welcome-message.enabled}")
    private boolean welcomeEmailEnabled;

    @Transactional
    public CustomerDto register(CreateCustomerDto dto) {
        validateEmailNotTaken(dto.getEmail());
        NexarUser user = createUser(dto);
        Customer customer = createCustomer(dto, user);
        sendWelcomeEmail(customer);
        return map(customer);
    }

    private void validateEmailNotTaken(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalStateException("Email already in use: " + email);
        }
    }

    private NexarUser createUser(CreateCustomerDto dto) {
        NexarUser user = new NexarUser();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setNexarRole(NexarUser.NexarRole.REGULAR_CUSTOMER);
        return userRepository.save(user);
    }

    private Customer createCustomer(CreateCustomerDto dto, NexarUser user) {
        Customer customer = new Customer();
        customer.setUser(user);
        customer.setDriverLicenseNo(dto.getDriverLicenseNo());
        customer.setDateOfBirth(dto.getDateOfBirth());
        customer.setAddress(dto.getAddress());
        customer.setCity(dto.getCity());
        return customerRepository.save(customer);
    }

    private void sendWelcomeEmail(Customer customer) {
        if (!welcomeEmailEnabled) {
            log.debug("Welcome email is disabled, skipping for customer {}", customer.getId());
            return;
        }

        try {
            emailService.sendWelcomeMessage(customer);
        } catch (Exception exception) {
            log.warn("Welcome email failed for customer {}, but registration succeeded", customer.getId(), exception);
        }
    }
}