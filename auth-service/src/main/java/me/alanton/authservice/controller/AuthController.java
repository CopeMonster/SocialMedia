package me.alanton.authservice.controller;

import lombok.RequiredArgsConstructor;
import me.alanton.authservice.dto.request.SignInRequest;
import me.alanton.authservice.dto.request.SignUpRequest;
import me.alanton.authservice.dto.response.SignInResponse;
import me.alanton.authservice.dto.response.SignUpResponse;
import me.alanton.authservice.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
        SignUpResponse signUpResponse = authService.signUp(signUpRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(signUpResponse);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest signInRequest) {
        SignInResponse signInResponse = authService.signIn(signInRequest);

        return ResponseEntity.ok(signInResponse);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<SignInResponse> refreshToken() {
        return null;
    }

    @PostMapping("/log-out")
    public void logout() {

    }
}
