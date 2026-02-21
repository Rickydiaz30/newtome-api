package com.newtome.newtomeapi.users.dto;

public record UserMeResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phone
) {}
