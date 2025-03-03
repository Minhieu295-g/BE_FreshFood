package com.freshfood.controller;

import com.freshfood.dto.request.SignInRequest;
import com.freshfood.dto.response.ResponseData;
import com.freshfood.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@Valid
@Tag(name = "Authentication Controller")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

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

//    @GetMapping("login-with-google")
//    public ResponseData<?> getUrlLoginWithGoogle(HttpServletRequest request) {
//        return new ResponseData<>(HttpStatus.OK.value(), "Get Url success", loginProviderService.generateGoogleLoginUrl());
//    }

}
