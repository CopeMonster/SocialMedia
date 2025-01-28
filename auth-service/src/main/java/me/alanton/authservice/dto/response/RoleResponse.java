package me.alanton.authservice.dto.response;

import lombok.Builder;

@Builder
public record RoleResponse(
        String name
) {
}
