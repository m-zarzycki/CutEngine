package com.cutengine.mapper;

import com.cutengine.dto.UserResponse;
import com.cutengine.entity.User;

public class UserMapper {
    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getPhone(),
                user.getRole()
        );
    }
}
