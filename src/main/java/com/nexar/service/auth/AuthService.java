package com.nexar.service.auth;

import com.nexar.controller.auth.dto.UserLoginRequestDto;
import com.nexar.service.auth.jwt.TokenProvider;
import com.nexar.service.user.NexarUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final NexarUserService userService;

    public String authorize(UserLoginRequestDto dto) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
        var authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        userService.findByEmail(dto.getEmail());

        return tokenProvider.createToken(authentication, false);
    }
}