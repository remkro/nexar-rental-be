package com.nexar.service.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TokenProvider {
    private static final String AUTHORITIES_KEY = "auth";

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.validity.in-seconds:86400}")
    private long validityInSeconds;

    @Value("${jwt.validity.in-seconds-for-remember-me:2592000}")
    private long validityInSecondsForRememberMe;

    private Key key;

    @PostConstruct
    public void init() {
        byte[] keyBytes;
        if (StringUtils.hasText(secretKey) && !isBase64(secretKey)) {
            log.warn("JWT secret is not Base64-encoded.");
            keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        } else {
            log.debug("Using Base64-encoded JWT secret key");
            keyBytes = Decoders.BASE64.decode(secretKey);
        }
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Authentication authentication, boolean rememberMe) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date now = new Date();
        long validity = rememberMe ? validityInSecondsForRememberMe : validityInSeconds;
        Date expiration = new Date(now.getTime() + validity * 1000L);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(now)
                .claim(AUTHORITIES_KEY, authorities)
                .claim("userId", extractUserId(authentication))
                .claim("email", authentication.getName())
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expiration)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .filter(StringUtils::hasText)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.warn("JWT token expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.warn("Unsupported JWT token: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.warn("Malformed JWT token: {}", e.getMessage());
        } catch (SignatureException e) {
            log.warn("Invalid JWT signature: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.warn("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private String extractUserId(Authentication authentication) {
        if (authentication.getPrincipal() instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        }
        return authentication.getName();
    }

    private boolean isBase64(String value) {
        try {
            Decoders.BASE64.decode(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}