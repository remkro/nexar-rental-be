package com.nexar.service.email;

import com.nexar.dao.model.customer.Customer;

public interface EmailService {
    void sendWelcomeMessage(Customer customer);
}