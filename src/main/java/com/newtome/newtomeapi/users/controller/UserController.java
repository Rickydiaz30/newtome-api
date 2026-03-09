package com.newtome.newtomeapi.users.controller;

import com.newtome.newtomeapi.common.ApiResponse;
import com.newtome.newtomeapi.users.dto.UserMeResponse;
import com.newtome.newtomeapi.users.mapper.UserMapper;
import com.newtome.newtomeapi.users.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;


    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserMeResponse>> me(Authentication authentication) {

        String username = authentication.getName();

        var user = userRepository
                .findByUsernameIgnoreCase(username)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
                );

        return ResponseEntity.ok(
                new ApiResponse<>(true, "User loaded", UserMapper.toUserMeResponse(user))
        );
    }
}
