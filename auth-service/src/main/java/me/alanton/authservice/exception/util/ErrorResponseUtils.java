package me.alanton.authservice.exception.util;

import me.alanton.authservice.exception.response.ErrorResponse;
import me.alanton.authservice.exception.response.InvalidParameterResponse;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponseUtils {
    public static ErrorResponse build(
            final String code,
            final String message,
            final HttpStatus httpStatus
    ) {
        return buildDetails(code, message, httpStatus);
    }

    public static ErrorResponse build(
            final String code,
            final String message,
            final HttpStatus httpStatus,
            final List<InvalidParameterResponse> invalidParameter
            ) {
        return buildDetails(code, message, httpStatus, invalidParameter);
    }

    private static ErrorResponse buildDetails(
            final String code,
            final String message,
            final HttpStatus httpStatus
    ) {
        return buildDetails(code, message, httpStatus, null);
    }

    private static ErrorResponse buildDetails(
            final String code,
            final String message,
            final HttpStatus httpStatus,
            final List<InvalidParameterResponse> invalidParameter
    ) {
        if (code == null || message == null || httpStatus == null) {
            throw new IllegalArgumentException("Parameters can't be null");
        }

        return ErrorResponse.builder()
                .code(code)
                .message(message)
                .status(httpStatus.value())
                .timestamp(LocalDateTime.now())
                .invalidParameter(invalidParameter)
                .build();
    }
}