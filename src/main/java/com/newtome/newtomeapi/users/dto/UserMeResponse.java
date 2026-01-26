package com.newtome.newtomeapi.users.dto;

public record UserMeResponse(
        Long id,
        String fullName,
        String email,
        String phone
) {}
