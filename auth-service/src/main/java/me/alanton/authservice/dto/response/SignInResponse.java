package me.alanton.authservice.dto.response;

import lombok.Builder;

@Builder
public record SignInResponse(
        String accessToken,
        String refreshToken
) {
}
