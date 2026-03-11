package com.newtome.newtomeapi.users.mapper;

import com.newtome.newtomeapi.users.dto.UserMeResponse;
import com.newtome.newtomeapi.users.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserMeResponse toMeResponse(User user) {
        return new UserMeResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getPhone(),
                user.getStreetAddress(),
                user.getCity(),
                user.getState(),
                user.getZipCode()
        );
    }
}