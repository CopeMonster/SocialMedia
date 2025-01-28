package me.alanton.authservice.exception.policy;

public interface ExceptionPolicy {
    String getCode();
    String getMessage();
}