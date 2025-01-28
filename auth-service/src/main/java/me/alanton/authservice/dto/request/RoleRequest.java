package me.alanton.authservice.dto.request;

import lombok.Builder;

@Builder
public record RoleRequest(
        String name
) {
}
