package com.newtome.newtomeapi.users.dto;

public record UserMeResponse(
        Long id,
        String firstName,
        String lastName,
        String username,
        String email,
        String phone,
        String streetAddress,
        String city,
        String state,
        String zipCode
) {}
