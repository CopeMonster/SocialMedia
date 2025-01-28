package me.alanton.authservice.exception.response;

import lombok.Builder;

@Builder
public record InvalidParameterResponse(
        String parameter,
        String message
) {
}