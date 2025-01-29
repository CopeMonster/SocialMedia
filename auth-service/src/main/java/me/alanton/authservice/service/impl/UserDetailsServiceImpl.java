package me.alanton.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.alanton.authservice.dto.response.UserResponse;
import me.alanton.authservice.service.UserServiceFeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserServiceFeignClient userServiceFeignClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ResponseEntity<UserResponse> userResponse = userServiceFeignClient.getUserByUsername(username);
        UserResponse userResponseBody = userResponse.getBody();

        if (userResponseBody == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new User(
                userResponseBody.username(),
                userResponseBody.password(),
                userResponseBody.roles().stream()
                        .map(roleResponse ->
                                new SimpleGrantedAuthority(roleResponse.name()))
                        .collect(Collectors.toSet())
        );
    }
}
