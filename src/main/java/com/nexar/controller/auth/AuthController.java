package com.nexar.controller.auth;

import com.nexar.controller.auth.dto.AuthTokenDto;
import com.nexar.controller.auth.dto.UserLoginRequestDto;
import com.nexar.service.auth.AuthService;
import com.nexar.service.auth.jwt.JWTFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final String BEARER = "Bearer ";

    @PostMapping
    public ResponseEntity<AuthTokenDto> login(@RequestBody UserLoginRequestDto dto) {
        log.info("Login request for: {}", dto.getEmail());
        var token = authService.authorize(dto);

        var httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, BEARER + token);

        return new ResponseEntity<>(
                AuthTokenDto.builder().idToken(token).build(),
                httpHeaders,
                HttpStatus.OK);
    }
}
