package me.alanton.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.alanton.userservice.dto.request.UserRequest;
import me.alanton.userservice.dto.response.UserResponse;
import me.alanton.userservice.entity.Role;
import me.alanton.userservice.entity.User;
import me.alanton.userservice.exception.impl.BusinessException;
import me.alanton.userservice.exception.impl.BusinessExceptionReason;
import me.alanton.userservice.mapper.UserMapper;
import me.alanton.userservice.repository.RoleRepository;
import me.alanton.userservice.repository.UserRepository;
import me.alanton.userservice.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public UserResponse getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new BusinessException(BusinessExceptionReason.USER_NOT_FOUND_EXCEPTION));

        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new BusinessException(BusinessExceptionReason.USER_NOT_FOUND_EXCEPTION));

        return userMapper.toUserResponse(user);
    }

    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);

        return users.map(userMapper::toUserResponse);
    }

    @Override
    public UserResponse saveUser(UserRequest userRequest) {
        userRepository.findByUsername(userRequest.username())
                .ifPresent(user -> {
                    throw new BusinessException(BusinessExceptionReason.USER_IS_ALREADY_EXIST);
                });

        Role role = roleRepository.findByName("USER")
                .orElseThrow(() -> new BusinessException(BusinessExceptionReason.ROLE_NOT_FOUND_EXCEPTION));

        User user = User.builder()
                .username(userRequest.username())
                .firstname(userRequest.firstname())
                .lastname(userRequest.lastname())
                .email(userRequest.email())
                .password(passwordEncoder.encode(userRequest.password()))
                .roles(Set.of(role))
                .build();

        userRepository.save(user);

        return userMapper.toUserResponse(user);
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.findById(id)
                .orElseThrow(() ->
                        new BusinessException(BusinessExceptionReason.USER_NOT_FOUND_EXCEPTION));

        userRepository.deleteById(id);
    }
}
