package me.alanton.authservice.dto.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record SignUpResponse(
        UUID id,
        String username,
        String firstname,
        String lastname,
        String email
) {
}
