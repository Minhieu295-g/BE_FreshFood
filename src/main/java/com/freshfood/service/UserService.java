package com.freshfood.service;

import com.freshfood.dto.request.UserRequestDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDetailsService userDetails();
    int addUser(UserRequestDTO userRequestDTO);

}
