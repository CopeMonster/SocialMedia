package me.alanton.userservice.exception.policy;

public interface ExceptionPolicy {
    String getCode();
    String getMessage();
}