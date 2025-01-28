package me.alanton.authservice.dto.request;

import lombok.Builder;

@Builder
public record SignUpRequest(
        String username,
        String firstname,
        String lastname,
        String email,
        String password
) {
}
