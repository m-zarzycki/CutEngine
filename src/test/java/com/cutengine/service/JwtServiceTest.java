package com.cutengine.service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;

import static org.assertj.core.api.Assertions.*;

public class JwtServiceTest {
    private static final String SECRET = "9cbbacad327af4dbbac953d010fc6de3a63d2dfc4d759ff3fd933bbc4ff2402a";
    private static final String OTHER_SECRET = "4f8b2e91c7a3d065e81f4b2a9c7d3e601a5f8c2d9b4e7103a6c8d5f2e9b1a473";
    private static final Duration EXPIRATION = Duration.ofMinutes(15);
    private static final String USERNAME = "jan";
    private static final String OTHER_USERNAME = "anna";
    JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService(SECRET, EXPIRATION);
    }

    @Test
    void shouldExtractUsernameFromGeneratedToken() {
        String token = jwtService.generateToken(USERNAME);

        String username = jwtService.extractUsername(token);

        assertThat(username).isEqualTo(USERNAME);
    }

    @Test
    void shouldRejectTamperedToken() {
        String token = jwtService.generateToken(USERNAME);
        String modifiedToken = token + "z";

        assertThatThrownBy(() -> jwtService.extractUsername(modifiedToken)).isInstanceOf(SignatureException.class);
    }

    @Test
    void shouldRejectTokenSignedWithDifferentKey() {
        JwtService otherJwtService = new JwtService(OTHER_SECRET, EXPIRATION);
        String tokenSignedWithDifferentKey = otherJwtService.generateToken(USERNAME);

        assertThatThrownBy(() -> jwtService.extractUsername(tokenSignedWithDifferentKey)).isInstanceOf(SignatureException.class);
    }

    @Test
    void shouldRejectExpiredToken() {
        JwtService expiredJwtService = new JwtService(SECRET, Duration.ofMinutes(-1));
        String expiredToken = expiredJwtService.generateToken(USERNAME);

        assertThatThrownBy(() -> jwtService.extractUsername(expiredToken)).isInstanceOf(ExpiredJwtException.class);
    }

    @Test
    void shouldValidateTokenForMatchingUser() {
        String token = jwtService.generateToken(USERNAME);
        UserDetails userDetails = userDetails(USERNAME);

        boolean outcome = jwtService.isTokenValid(token, userDetails);

        assertThat(outcome).isTrue();
    }

    @Test
    void shouldNotValidateTokenForDifferentUser() {
        String token = jwtService.generateToken(USERNAME);
        UserDetails userDetails = userDetails(OTHER_USERNAME);

        boolean outcome = jwtService.isTokenValid(token, userDetails);

        assertThat(outcome).isFalse();
    }

    private static UserDetails userDetails(String username) {
        return User.builder()
                .username(username)
                .password("password")
                .roles("CUSTOMER")
                .build();
    }
}
