package com.newtome.newtomeapi.users.controller;

import com.newtome.newtomeapi.common.ApiResponse;
import com.newtome.newtomeapi.users.dto.UpdateUserRequest;
import com.newtome.newtomeapi.users.dto.UserMeResponse;
import com.newtome.newtomeapi.users.mapper.UserMapper;
import com.newtome.newtomeapi.users.model.User;
import com.newtome.newtomeapi.users.repository.UserRepository;
import com.newtome.newtomeapi.users.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserService userService;

    public UserController(
            UserRepository userRepository,
            UserService userService,
            UserMapper userMapper
    ) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserMeResponse>> me(Authentication authentication) {

        String username = authentication.getName();

        User user = userRepository
                .findByUsernameIgnoreCase(username)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
                );

        return ResponseEntity.ok(
                new ApiResponse<>(true, "User loaded", userMapper.toMeResponse(user))
        );
    }

    @PatchMapping("/me")
    public UserMeResponse updateMe(
            Authentication authentication,
            @RequestBody UpdateUserRequest req
    ) {

        String username = authentication.getName();

        User user = userRepository
                .findByUsernameIgnoreCase(username)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
                );

        return userMapper.toMeResponse(
                userService.updateUser(user.getId(), req)
        );
    }
}