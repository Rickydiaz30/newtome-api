package com.newtome.newtomeapi.users.controller;

import com.newtome.newtomeapi.common.ApiResponse;
import com.newtome.newtomeapi.security.JwtService;
import com.newtome.newtomeapi.users.dto.LoginRequest;
import com.newtome.newtomeapi.users.dto.RegisterRequest;
import com.newtome.newtomeapi.users.model.User;
import com.newtome.newtomeapi.users.repository.UserRepository;
import com.newtome.newtomeapi.users.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtService jwtService, UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @GetMapping("/whoami")
    public ResponseEntity<?> whoami() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        java.util.HashMap<String, Object> body = new java.util.HashMap<>();
        body.put("authenticated", false);
        body.put("principal", null);

        if (auth == null) {
            return ResponseEntity.ok(body);
        }

        Object principalObj = auth.getPrincipal();
        if (principalObj == null || "anonymousUser".equals(principalObj)) {
            return ResponseEntity.ok(body);
        }

        String principal;
        if (principalObj instanceof String s) {
            principal = s;
        } else if (principalObj instanceof UserDetails ud) {
            principal = ud.getUsername();
        } else {
            principal = principalObj.toString();
        }

        body.put("authenticated", true);
        body.put("principal", principal);
        body.put("authType", auth.getClass().getSimpleName());

        return ResponseEntity.ok(body);
    }


    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@Valid @RequestBody RegisterRequest req) {

        User saved = userService.register(req);

        return ResponseEntity
                .created(URI.create("/api/users/" + saved.getId()))
                .body(new ApiResponse<>(true, "User registered", null));
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {

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

        // Generate JWT using username as identity
        String token = jwtService.generateToken(user);

        return ResponseEntity.ok(new ApiResponse<>(true, "Login ok", Map.of("token", token)));
    }
}
