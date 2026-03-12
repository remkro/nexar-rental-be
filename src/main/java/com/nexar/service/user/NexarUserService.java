package com.nexar.service.user;

import com.nexar.dao.model.user.NexarUser;
import com.nexar.dao.repository.user.NexarUserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NexarUserService {
    private final NexarUserRepository userRepository;

    public NexarUser findByEmail(String email) {
        return userRepository
                .findOneByEmailIgnoreCase(email)
                .orElseThrow(() -> new EntityNotFoundException("User with email " + email + " not found"));
    }
}

