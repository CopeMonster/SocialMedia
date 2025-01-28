package me.alanton.authservice.exception.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.alanton.authservice.exception.policy.BusinessExceptionPolicy;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
public class BusinessException extends RuntimeException implements BusinessExceptionPolicy {
    final String code;
    final String message;
    final HttpStatus httpStatus;

    public BusinessException(final BusinessExceptionReason reason) {
        this(reason, (HttpStatus) null);
    }

    public BusinessException(final BusinessExceptionReason reason, HttpStatus overridingHttpStatus) {
        this(reason, overridingHttpStatus, (Object[]) null);
    }

    public BusinessException(final BusinessExceptionReason reason, Object... parameters) {
        this(reason, null, parameters);
    }

    public BusinessException(final BusinessExceptionReason reason, HttpStatus overridingHttpStatus, Object... parameters) {
        this.code = getCode();

        if (parameters != null) {
            this.message = String.format(reason.getMessage(), parameters);
        } else {
            this.message = reason.getMessage();
        }

        this.httpStatus = overridingHttpStatus != null ? overridingHttpStatus : reason.getHttpStatus();
    }

    @Override
    public String getLocalizedMessage() {
        return message;
    }
}