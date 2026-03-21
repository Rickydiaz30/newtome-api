package com.newtome.newtomeapi.users.service;

import com.newtome.newtomeapi.security.JwtService;
import com.newtome.newtomeapi.users.dto.LoginRequest;
import com.newtome.newtomeapi.users.model.User;
import com.newtome.newtomeapi.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;

    private AuthService authService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        jwtService = mock(JwtService.class);

        authService = new AuthService(userRepository, passwordEncoder, jwtService);
    }

    @Test
    void login_shouldReturnToken_whenCredentialsAreValid() {
        // given
        var request = new LoginRequest();
        request.setUsername("testuser");
        request.setPassword("password");

        var user = new User();
        user.setUsername("testuser");
        user.setPasswordHash("encoded");

        when(userRepository.findByUsernameIgnoreCase("testuser"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("password", "encoded"))
                .thenReturn(true);

        when(jwtService.generateToken(user))
                .thenReturn("mock-token");

        // when
        var response = authService.login(request);

        // then
        assertTrue(response.isSuccess());

        var data = (Map<?, ?>) response.getData();
        assertEquals("mock-token", data.get("token"));
        verify(userRepository).updateLastLoginAt(eq("testuser"), any());
    }

    @Test
    void login_shouldThrowUnauthorized_whenPasswordIsWrong() {
        // given
        var request = new LoginRequest();
        request.setUsername("testuser");
        request.setPassword("wrong");

        var user = new User();
        user.setUsername("testuser");
        user.setPasswordHash("encoded");

        when(userRepository.findByUsernameIgnoreCase("testuser"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("wrong", "encoded"))
                .thenReturn(false);

        // when + then
        assertThrows(ResponseStatusException.class, () -> {
            authService.login(request);
        });

        // important: verify JWT NOT called
        verify(jwtService, never()).generateToken(any());
        verify(userRepository, never()).updateLastLoginAt(any(), any());
    }

    @Test
    void login_shouldThrowUnauthorized_whenUserNotFound() {
        // given
        var request = new LoginRequest();
        request.setUsername("unknown");
        request.setPassword("password");

        when(userRepository.findByUsernameIgnoreCase("unknown"))
                .thenReturn(Optional.empty());

        // when + then
        assertThrows(ResponseStatusException.class, () -> {
            authService.login(request);
        });

        verify(passwordEncoder, never()).matches(any(), any());
        verify(jwtService, never()).generateToken(any());
        verify(userRepository, never()).updateLastLoginAt(any(), any());
    }
}