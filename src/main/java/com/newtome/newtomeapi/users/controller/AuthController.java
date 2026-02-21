package com.newtome.newtomeapi.users.controller;

import com.newtome.newtomeapi.security.JwtService;
import com.newtome.newtomeapi.users.dto.LoginRequest;
import com.newtome.newtomeapi.users.dto.RegisterRequest;
import com.newtome.newtomeapi.users.model.User;
import com.newtome.newtomeapi.users.repository.UserRepository;
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

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
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
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {

        String normalizedEmail = req.getEmail().trim().toLowerCase();

        if (userRepository.existsByEmail(normalizedEmail)) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email already in use."));
        }

        User user = new User();

        user.setFirstName(req.getFirstName().trim());
        user.setLastName(req.getLastName().trim());
        user.setEmail(normalizedEmail);
        user.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        user.setPhone(req.getPhone() == null ? null : req.getPhone().trim());
        User saved = userRepository.save(user);

        return ResponseEntity
                .created(URI.create("/api/users/" + saved.getId()))
                .body(Map.of("message", "User registered."));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {

        String normalizedEmail = req.getEmail().trim().toLowerCase();

        User user = userRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

        boolean matches = passwordEncoder.matches(req.getPassword(), user.getPasswordHash());

        if (!matches) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        // ✅ Generate JWT and return it
        String token = jwtService.generateToken(user);


        return ResponseEntity.ok(Map.of(
                "message", "Login ok",
                "token", token
        ));
    }
}
