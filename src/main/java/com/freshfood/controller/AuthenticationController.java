package com.freshfood.controller;

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
                .cartId(tokenResponse.getCartId())
                .build();
        return new ResponseData<>(HttpStatus.OK.value(), "Login successfully!", userLoginResponse);
    }
    @PostMapping("/refresh")
    public ResponseData<?> refresh(HttpServletRequest request) {
        TokenResponse tokenResponse =  authenticationService.refreshToken(request);
        UserLoginResponse userLoginResponse = UserLoginResponse.builder()
                .username(tokenResponse.getUsername())
                .userId(tokenResponse.getUserId())
                .cartId(tokenResponse.getCartId())
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
    public ResponseData<?> googleCallback(@RequestParam("code") String code) {
        String accessToken = loginGoogleService.getGoogleAccessToken(code);
        if (accessToken == null) {
            return new ResponseData<>(HttpStatus.BAD_REQUEST.value(),"Failed to get access token" );
        }
        Map<String, Object> userInfo = loginGoogleService.getGoogleUserInfo(accessToken);
        if (userInfo == null) {
            return new ResponseData<>(HttpStatus.BAD_REQUEST.value(),"Failed to fetch userInfor");
        }
        userInfo.put("provider", "google");
        userInfo.put("access_token", accessToken);
        return new ResponseData<>(HttpStatus.OK.value(), "Login Successfully", authenticationService.signInWithProvider(userInfo));
    }
    @GetMapping("/facebook/callback")
    public ResponseData<?> facebookCallback(@RequestParam("code") String code) {
        String accessToken = loginFacebookService.getFacebookAccessToken(code);
        if (accessToken == null) {
            return new ResponseData<>(HttpStatus.BAD_REQUEST.value(),"Failed to get access token" );
        }
        Map<String, Object> userInfo = loginFacebookService.getFacebookUserInfo(accessToken);
        if (userInfo == null) {
            return new ResponseData<>(HttpStatus.BAD_REQUEST.value(),"Failed to fetch userInfor");
        }
        userInfo.put("provider", "facebook");
        userInfo.put("access_token", accessToken);
        return new ResponseData<>(HttpStatus.OK.value(), "Login Successfully", authenticationService.signInWithProvider(userInfo));
    }

}
