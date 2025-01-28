package me.alanton.authservice.service.impl;

import feign.FeignException;
import feign.Response;
import lombok.RequiredArgsConstructor;
import me.alanton.authservice.dto.request.SignInRequest;
import me.alanton.authservice.dto.request.SignUpRequest;
import me.alanton.authservice.dto.request.UserRequest;
import me.alanton.authservice.dto.response.SignInResponse;
import me.alanton.authservice.dto.response.SignUpResponse;
import me.alanton.authservice.dto.response.UserResponse;
import me.alanton.authservice.exception.impl.BusinessException;
import me.alanton.authservice.exception.impl.BusinessExceptionReason;
import me.alanton.authservice.service.AuthService;
import me.alanton.authservice.service.UserServiceFeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final UserServiceFeignClient userServiceFeignClient;

    @Override
    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        try {
            userServiceFeignClient.getUserByUsername(signUpRequest.username());
            throw new BusinessException(BusinessExceptionReason.USER_IS_ALREADY_EXIST);
        } catch (FeignException.NotFound ex) {

        }

        UserRequest userRequest = UserRequest.builder()
                .username(signUpRequest.username())
                .firstname(signUpRequest.firstname())
                .lastname(signUpRequest.lastname())
                .email(signUpRequest.email())
                .password(signUpRequest.password())
                .build();

        ResponseEntity<UserResponse> userResponse = userServiceFeignClient.saveUser(userRequest);
        UserResponse body = userResponse.getBody();

        return SignUpResponse.builder()
                .id(body.id())
                .username(body.username())
                .firstname(body.firstname())
                .lastname(body.lastname())
                .email(body.email())
                .build();
    }

    @Override
    public SignInResponse signIn(SignInRequest signInRequest) {

    }
}
