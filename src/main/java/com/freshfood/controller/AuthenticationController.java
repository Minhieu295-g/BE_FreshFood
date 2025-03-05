package com.freshfood.controller;

import com.freshfood.dto.request.SignInRequest;
import com.freshfood.dto.response.ResponseData;
import com.freshfood.service.AuthenticationService;

import com.freshfood.service.UserService;
import com.freshfood.service.impl.LoginProviderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@Valid
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final LoginProviderService loginProviderService;
    @PostMapping("/access")
    public ResponseEntity<?> login(@RequestBody SignInRequest signInRequest) {
        return new ResponseEntity<>(authenticationService.authenticate(signInRequest), HttpStatus.OK);
    }
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request) {
        return new ResponseEntity<>(authenticationService.refreshToken(request), HttpStatus.OK);
    }
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        return authenticationService.logout(request);
    }

    @GetMapping("/login-with-google")
    public ResponseData<?> getUrlLoginWithGoogle(HttpServletRequest request) {
        return new ResponseData<>(HttpStatus.OK.value(), "Get Url success", loginProviderService.generateGoogleLoginUrl());
    }

    @GetMapping("/google/callback")
    public ResponseEntity<Map<String, Object>> googleCallback(@RequestParam("code") String code) {
        String accessToken = loginProviderService.getGoogleAccessToken(code);
        if (accessToken == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Failed to get access token"));
        }
        Map<String, Object> userInfo = loginProviderService.getGoogleUserInfo(accessToken);
        if (userInfo == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Failed to fetch user info"));
        }

        userInfo.put("access_token", accessToken);
        return ResponseEntity.ok(userInfo);
    }

}
