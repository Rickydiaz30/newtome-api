package com.newtome.newtomeapi.users.mapper;

import com.newtome.newtomeapi.users.dto.UserMeResponse;
import com.newtome.newtomeapi.users.model.User;

public class UserMapper {

    public static UserMeResponse toUserMeResponse(User user) {
        return new UserMeResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getPhone()
        );
    }

}