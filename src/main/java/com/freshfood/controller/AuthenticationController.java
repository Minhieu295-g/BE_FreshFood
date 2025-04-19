package com.freshfood.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freshfood.dto.request.SignInRequest;
import com.freshfood.dto.response.ResponseData;
import com.freshfood.dto.response.TokenResponse;
import com.freshfood.dto.response.UserLoginResponse;
import com.freshfood.service.AuthenticationService;

import com.freshfood.service.impl.LoginFacebookService;
import com.freshfood.service.impl.LoginGoogleService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@Slf4j
@Valid
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final LoginGoogleService loginGoogleService;
    private final LoginFacebookService loginFacebookService;
    @PostMapping("/access")
    public ResponseData<?> login(@RequestBody SignInRequest signInRequest, HttpServletResponse response) {
        TokenResponse tokenResponse = authenticationService.authenticate(signInRequest);
        Cookie accessTokenCookie = new Cookie("access_token", tokenResponse.getAccessToken());
        Cookie refreshTokenCookie = new Cookie("refresh_token", tokenResponse.getRefreshToken());
        accessTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/");
        refreshTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(60 * 60); // 1 giờ
        refreshTokenCookie.setMaxAge(60 * 60 * 24 * 7);
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

        UserLoginResponse userLoginResponse = UserLoginResponse.builder()
                .username(tokenResponse.getUsername())
                .userId(tokenResponse.getUserId())
                .fullName(tokenResponse.getFullName())
                .cartId(tokenResponse.getCartId())
                .role(tokenResponse.getRole().toUpperCase())
                .build();
        return new ResponseData<>(HttpStatus.OK.value(), "Login successfully!", userLoginResponse);
    }
    @PostMapping("/refresh")
    public ResponseData<?> refresh(HttpServletRequest request) {
        TokenResponse tokenResponse =  authenticationService.refreshToken(request);
        UserLoginResponse userLoginResponse = UserLoginResponse.builder()
                .username(tokenResponse.getUsername())
                .userId(tokenResponse.getUserId())
                .fullName(tokenResponse.getFullName())
                .cartId(tokenResponse.getCartId())
                .role(tokenResponse.getRole().toUpperCase())
                .build();
        return new ResponseData<>(HttpStatus.OK.value(), "Refresh Token successfully!", userLoginResponse );
    }
    @PostMapping("/logout")
    public ResponseData<?> logout(HttpServletRequest request, HttpServletResponse response) {
        authenticationService.logout(request);
        Cookie accessTokenCookie = new Cookie("access_token", "");
        Cookie refreshTokenCookie = new Cookie("refesh_token", "");
        accessTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/");
        refreshTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(0); // Xóa ngay lập tức
        refreshTokenCookie.setMaxAge(0);
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
        return new ResponseData<>(HttpStatus.OK.value(), "Logout successfully!");
    }

    @GetMapping("/login-with-google")
    public ResponseData<?> getUrlLoginWithGoogle(HttpServletRequest request) {
        return new ResponseData<>(HttpStatus.OK.value(), "Get Url success", loginGoogleService.generateGoogleLoginUrl());
    }

    @GetMapping("/login-with-facebook")
    public ResponseData<?> getUrlLoginWithFacebook(HttpServletRequest request) {
        return new ResponseData<>(HttpStatus.OK.value(), "Get Url success", loginFacebookService.generaFacebookLoginUrl());
    }

    @GetMapping("/google/callback")
    public RedirectView googleCallback(@RequestParam("code") String code, HttpServletResponse response) throws JsonProcessingException {
        String accessToken = loginGoogleService.getGoogleAccessToken(code);
        if (accessToken == null) {
            return new RedirectView("http://localhost:3000/auth/callback?error=access_token_failed");
        }

        Map<String, Object> userInfo = loginGoogleService.getGoogleUserInfo(accessToken);
        if (userInfo == null) {
            return new RedirectView("http://localhost:3000/auth/callback?error=user_info_failed");
        }
        userInfo.put("provider", "google");
        userInfo.put("access_token", accessToken);

        TokenResponse tokenResponse =  authenticationService.signInWithProvider(userInfo);
        UserLoginResponse userLoginResponse = UserLoginResponse.builder()
                .username(tokenResponse.getUsername())
                .userId(tokenResponse.getUserId())
                .fullName(tokenResponse.getFullName())
                .cartId(tokenResponse.getCartId())
                .build();
        Cookie accessTokenCookie = new Cookie("access_token", tokenResponse.getAccessToken());
        Cookie refreshTokenCookie = new Cookie("refresh_token", tokenResponse.getRefreshToken());
        accessTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/");
        refreshTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(60 * 60); // 1 giờ
        refreshTokenCookie.setMaxAge(60 * 60 * 24 * 7);
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
        String userJson = new ObjectMapper().writeValueAsString(userLoginResponse);
        String encodedUser = URLEncoder.encode(userJson, StandardCharsets.UTF_8);
        return new RedirectView("http://localhost:3000/auth/callback?user=" + encodedUser);
    }
    @GetMapping("/facebook/callback")
    public RedirectView facebookCallback(@RequestParam("code") String code, HttpServletResponse response) throws JsonProcessingException {
        String accessToken = loginFacebookService.getFacebookAccessToken(code);
        if (accessToken == null) {
            return new RedirectView("http://localhost:3000/auth/callback?error=access_token_failed");
        }
        Map<String, Object> userInfo = loginFacebookService.getFacebookUserInfo(accessToken);
        if (userInfo == null) {
            return new RedirectView("http://localhost:3000/auth/callback?error=user_info_failed");
        }
        userInfo.put("provider", "facebook");
        userInfo.put("access_token", accessToken);
        TokenResponse tokenResponse =  authenticationService.signInWithProvider(userInfo);
        UserLoginResponse userLoginResponse = UserLoginResponse.builder()
                .username(tokenResponse.getUsername())
                .userId(tokenResponse.getUserId())
                .fullName(tokenResponse.getFullName())
                .cartId(tokenResponse.getCartId())
                .build();
        Cookie accessTokenCookie = new Cookie("access_token", tokenResponse.getAccessToken());
        Cookie refreshTokenCookie = new Cookie("refresh_token", tokenResponse.getRefreshToken());
        accessTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/");
        refreshTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(60 * 60); // 1 giờ
        refreshTokenCookie.setMaxAge(60 * 60 * 24 * 7);
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
        String userJson = new ObjectMapper().writeValueAsString(userLoginResponse);
        String encodedUser = URLEncoder.encode(userJson, StandardCharsets.UTF_8);
        return new RedirectView("http://localhost:3000/auth/callback?user=" + encodedUser);    }

}
