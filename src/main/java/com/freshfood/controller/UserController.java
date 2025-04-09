package com.freshfood.controller;

import com.freshfood.dto.request.UserRequestDTO;
import com.freshfood.dto.response.ResponseData;
import com.freshfood.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    public ResponseData<?> updateUser(@RequestBody UserRequestDTO userRequestDTO) {
        return new ResponseData<>(HttpStatus.OK.value(), "Updated user successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseData<?> deleteUser(@PathVariable int id) {
        return new ResponseData<>(HttpStatus.OK.value(), "Deleted user successfully");
    }
    @GetMapping("/{id}")
    public ResponseData<?> getUser(@PathVariable int id) {
        return new ResponseData<>(HttpStatus.OK.value(), "Get user successfully");
    }

    @GetMapping("/list")
    public ResponseData<?> getUsers(@RequestParam(required = false, defaultValue = "0") int pageNo, @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return new ResponseData<>(HttpStatus.OK.value(), "Get list user successfully", userService.getUsers(pageNo, pageSize));
    }

}
