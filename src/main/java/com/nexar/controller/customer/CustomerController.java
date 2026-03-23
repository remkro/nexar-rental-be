package com.nexar.controller.customer;

import com.nexar.controller.customer.dto.CreateCustomerDto;
import com.nexar.controller.customer.dto.CustomerDto;
import com.nexar.service.customer.CustomerService;
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
@RequestMapping("/customer")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<CustomerDto> register(@Valid @RequestBody CreateCustomerDto dto) {
        log.info("Register customer request, email: {}, first name: {}, last name:{}",
                dto.getEmail(), dto.getFirstName(), dto.getLastName());

        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.register(dto));
    }
}
