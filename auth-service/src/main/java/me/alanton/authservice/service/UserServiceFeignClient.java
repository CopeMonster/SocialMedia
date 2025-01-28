package me.alanton.authservice.service;

import me.alanton.authservice.dto.request.UserRequest;
import me.alanton.authservice.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "user-service")
public interface UserServiceFeignClient {
    @GetMapping("/api/v1/users/{id}")
    ResponseEntity<UserResponse> getUserById(@PathVariable UUID id);

    @GetMapping("/api/v1/users/{username}")
    ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username);

    @GetMapping("/api/v1/users")
    ResponseEntity<Page<UserResponse>> getAllUsers();

    @PostMapping("/api/v1/users")
    ResponseEntity<UserResponse> saveUser(@RequestBody UserRequest userRequest);

    @DeleteMapping("/api/v1/users/{id}")
    ResponseEntity<UserResponse> deleteUser(@PathVariable UUID id);
}
