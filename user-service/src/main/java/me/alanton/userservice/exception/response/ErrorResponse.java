package me.alanton.userservice.exception.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ErrorResponse(
        String code,
        String message,
        Integer status,
        LocalDateTime timestamp,
        List<InvalidParameterResponse> invalidParameter
) {
}