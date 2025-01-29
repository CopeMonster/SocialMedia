package me.alanton.authservice.service.impl;

import feign.FeignException;
import feign.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.alanton.authservice.dto.request.SignInRequest;
import me.alanton.authservice.dto.request.SignUpRequest;
import me.alanton.authservice.dto.request.UserRequest;
import me.alanton.authservice.dto.response.SignInResponse;
import me.alanton.authservice.dto.response.SignUpResponse;
import me.alanton.authservice.dto.response.UserResponse;
import me.alanton.authservice.exception.impl.BusinessException;
import me.alanton.authservice.exception.impl.BusinessExceptionReason;
import me.alanton.authservice.security.JwtUtils;
import me.alanton.authservice.service.AuthService;
import me.alanton.authservice.service.UserServiceFeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final UserServiceFeignClient userServiceFeignClient;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        UserRequest userRequest = UserRequest.builder()
                .username(signUpRequest.username())
                .firstname(signUpRequest.firstname())
                .lastname(signUpRequest.lastname())
                .email(signUpRequest.email())
                .password(signUpRequest.password())
                .build();

        ResponseEntity<UserResponse> userResponse = userServiceFeignClient.saveUser(userRequest);

        UserResponse userResponseBody = userResponse.getBody();

        return SignUpResponse.builder()
                .id(userResponseBody.id())
                .username(userResponseBody.username())
                .firstname(userResponseBody.firstname())
                .lastname(userResponseBody.lastname())
                .email(userResponseBody.email())
                .build();
    }

    @Override
    public SignInResponse signIn(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.username(),
                signInRequest.password()
        ));

        ResponseEntity<UserResponse> user =
                userServiceFeignClient.getUserByUsername(signInRequest.username());

        UserDetails userDetails = user.getBody();

        String accessToken = jwtUtils.generateAccessToken(userDetails);
        String refreshToken = jwtUtils.generateRefreshToken(userDetails);

        return SignInResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
