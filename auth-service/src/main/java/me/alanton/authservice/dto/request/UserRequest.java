package me.alanton.authservice.dto.request;

import lombok.Builder;

import java.util.Set;

@Builder
public record UserRequest(
        String username,
        String firstname,
        String lastname,
        String email,
        String password,
        Set<RoleRequest> roles
) {
}
