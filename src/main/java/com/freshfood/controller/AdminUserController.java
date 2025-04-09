package com.freshfood.controller;

import com.freshfood.dto.request.UserAdminRequestDTO;
import com.freshfood.dto.response.ResponseData;
import com.freshfood.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;

    @PostMapping("/")
    public ResponseData<?> addUser(@RequestBody UserAdminRequestDTO userRequestDTO) {
        return new ResponseData<>(HttpStatus.CREATED.value(), "User added by admin", userService.addUserByAdmin(userRequestDTO));
    }
    @PutMapping("/{id}")
    public ResponseData<?> updateUser(@PathVariable int id, @RequestBody UserAdminRequestDTO userRequestDTO) {
        userService.updateUserByAdmin(id, userRequestDTO);
        return new ResponseData<>(HttpStatus.CREATED.value(), "User added by admin");
    }
}
