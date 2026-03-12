package com.nexar.dao.repository.user;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.UUID;

public class SecurityUser extends User {
    private UUID userId;
    private String userFullName;

    public SecurityUser(UUID userId, String userFullName, String username, @Nullable String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
        this.userFullName = userFullName;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getUserFullName() {
        return userFullName;
    }
}
