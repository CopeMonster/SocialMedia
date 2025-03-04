package me.alanton.userservice.dto.response;

import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record UserResponse(
    UUID id,
    String username,
    String firstname,
    String lastname,
    String email,
    String password,
    Set<RoleResponse> roles
) {
}