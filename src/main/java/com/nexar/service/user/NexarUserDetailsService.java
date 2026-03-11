package com.nexar.service.user;

import com.nexar.dao.model.user.NexarUser;
import com.nexar.dao.repository.user.NexarUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class NexarUserDetailsService implements UserDetailsService {
    private final NexarUserRepository nexarUserRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        NexarUser user = nexarUserRepository.findOneByEmailIgnoreCase(email)
                .orElseThrow(() -> {
                    log.error("User not found for email: {}", email);
                    return new UsernameNotFoundException("User not found for email: " + email);
                });

        return new User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(
                        user.getNexarRole().isInternal() ? "ROLE_ADMIN" : "ROLE_USER"
                ))
        );
    }
}
