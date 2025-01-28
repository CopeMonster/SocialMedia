package me.alanton.userservice.dto.request;

import java.util.Set;

public record UserRequest(
        String username,
        String firstname,
        String lastname,
        String email,
        String password,
        Set<RoleRequest> roles
) {
}
