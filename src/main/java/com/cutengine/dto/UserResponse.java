package com.cutengine.dto;

import com.cutengine.entity.Role;

public record UserResponse (
    Long id,
    String firstName,
    String lastName,
    String username,
    String email,
    String phone,
    Role role
) {}
