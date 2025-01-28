package me.alanton.authservice.exception.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BusinessExceptionReason {
    USER_NOT_FOUND_EXCEPTION("User not found", HttpStatus.NOT_FOUND),
    USER_IS_ALREADY_EXIST("User is already exist", HttpStatus.BAD_REQUEST),
    ROLE_NOT_FOUND_EXCEPTION("Role not found", HttpStatus.NOT_FOUND);

    private final String code = BusinessExceptionReason.class.getSimpleName();
    private final String message;
    private final HttpStatus httpStatus;
}