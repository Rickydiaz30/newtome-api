package com.newtome.newtomeapi.users.service;

import com.newtome.newtomeapi.common.ApiResponse;
import com.newtome.newtomeapi.security.JwtService;
import com.newtome.newtomeapi.users.dto.LoginRequest;
import com.newtome.newtomeapi.users.model.User;
import com.newtome.newtomeapi.users.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public ApiResponse<?> login(LoginRequest req) {

        String normalizedUsername = req.getUsername().trim().toLowerCase();

        User user = userRepository
                .findByUsernameIgnoreCase(normalizedUsername)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials")
                );

        boolean matches = passwordEncoder.matches(
                req.getPassword(),
                user.getPasswordHash()
        );

        if (!matches) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        LocalDateTime now = LocalDateTime.now();
        userRepository.updateLastLoginAt(user.getUsername(), now);

        String token = jwtService.generateToken(user);

        return new ApiResponse<>(true, "Login ok", Map.of("token", token));
    }
}
