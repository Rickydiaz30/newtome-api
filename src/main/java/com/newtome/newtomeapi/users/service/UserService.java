package com.newtome.newtomeapi.users.service;

import com.newtome.newtomeapi.users.dto.RegisterRequest;
import com.newtome.newtomeapi.users.dto.UpdateUserRequest;
import com.newtome.newtomeapi.users.model.User;
import com.newtome.newtomeapi.users.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegisterRequest req) {

        String normalizedEmail = req.getEmail().trim().toLowerCase();
        String normalizedUsername = req.getUsername().trim().toLowerCase();

        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already in use");
        }

        if (userRepository.existsByUsername(normalizedUsername)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already taken");
        }

        User user = new User();

        user.setFirstName(req.getFirstName().trim());
        user.setLastName(req.getLastName().trim());
        user.setEmail(normalizedEmail);
        user.setUsername(normalizedUsername);
        user.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        user.setPhone(req.getPhone() == null ? null : req.getPhone().trim());
        user.setStreetAddress(req.getStreetAddress() == null ? null : req.getStreetAddress().trim());
        user.setCity(req.getCity() == null ? null : req.getCity().trim());
        user.setState(req.getState() == null ? null : req.getState().trim().toUpperCase());
        user.setZipCode(req.getZipCode() == null ? null : req.getZipCode().trim());

        return userRepository.save(user);
    }

    public User updateUser(Long userId, UpdateUserRequest req) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
                );

        if (req.getFirstName() != null) {
            user.setFirstName(req.getFirstName().trim());
        }

        if (req.getLastName() != null) {
            user.setLastName(req.getLastName().trim());
        }

        if (req.getPhone() != null) {
            user.setPhone(req.getPhone().trim());
        }

        if (req.getStreetAddress() != null) {
            user.setStreetAddress(req.getStreetAddress().trim());
        }

        if (req.getCity() != null) {
            user.setCity(req.getCity().trim());
        }

        if (req.getState() != null) {
            user.setState(req.getState().trim().toUpperCase());
        }

        if (req.getZipCode() != null) {
            user.setZipCode(req.getZipCode().trim());
        }

        return userRepository.save(user);
    }
}