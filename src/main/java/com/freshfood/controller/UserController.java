package com.freshfood.controller;

import com.freshfood.dto.request.UserRequestDTO;
import com.freshfood.dto.response.ResponseData;
import com.freshfood.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Validated
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/")
    public ResponseData<?> addUser(@RequestBody UserRequestDTO userRequestDTO) {
        return new ResponseData<>(HttpStatus.CREATED.value(), "User added successfully",userService.addUser(userRequestDTO));
    }
}
