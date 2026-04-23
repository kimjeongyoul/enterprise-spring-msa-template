package com.msa.platform.auth.controller;

import com.msa.platform.auth.dto.SignUpRequest;
import com.msa.platform.auth.service.AuthService;
import com.msa.platform.core.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ApiResponse<Void> signUp(@RequestBody SignUpRequest request) {
        authService.signUp(request);
        return ApiResponse.success(null);
    }
}
