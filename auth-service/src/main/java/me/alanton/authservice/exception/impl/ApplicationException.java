package me.alanton.authservice.exception.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.alanton.authservice.exception.policy.ApplicationExceptionPolicy;

@Getter
@Setter
@ToString
public class ApplicationException extends RuntimeException implements ApplicationExceptionPolicy {
    final String code;
    final String message;

    public ApplicationException(final ApplicationExceptionReason reason) {
        this(reason, (Object[]) null);
    }

    public ApplicationException(final ApplicationExceptionReason reason, Object... parameters) {
        this.code = reason.getCode();

        if (parameters != null) {
            this.message = String.format(reason.getMessage(), parameters);
        } else {
            this.message = reason.getMessage();
        }
    }

    @Override
    public String getLocalizedMessage() {
        return message;
    }
}