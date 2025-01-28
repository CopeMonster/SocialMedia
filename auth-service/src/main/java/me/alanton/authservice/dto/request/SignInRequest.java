package me.alanton.authservice.dto.request;

import lombok.Builder;

@Builder
public record SignInRequest(
        String username,
        String password
) {
}
