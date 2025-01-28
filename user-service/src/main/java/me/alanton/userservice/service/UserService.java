package me.alanton.userservice.service;

import me.alanton.userservice.dto.request.UserRequest;
import me.alanton.userservice.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {
    UserResponse getUserById(UUID id);
    UserResponse getUserByUsername(String username);
    Page<UserResponse> getAllUsers(Pageable pageable);
    UserResponse saveUser(UserRequest userRequest);
    void deleteUser(UUID id);
}
