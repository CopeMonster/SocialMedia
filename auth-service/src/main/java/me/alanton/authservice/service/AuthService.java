package me.alanton.authservice.service;

import me.alanton.authservice.dto.request.SignInRequest;
import me.alanton.authservice.dto.request.SignUpRequest;
import me.alanton.authservice.dto.response.SignInResponse;
import me.alanton.authservice.dto.response.SignUpResponse;

public interface AuthService {
    SignUpResponse signUp(SignUpRequest signUpRequest);
    SignInResponse signIn(SignInRequest signInRequest);
}
