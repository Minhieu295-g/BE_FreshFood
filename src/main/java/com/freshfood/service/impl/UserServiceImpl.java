package com.freshfood.service.impl;

import com.freshfood.dto.request.UserRequestDTO;
import com.freshfood.model.Cart;
import com.freshfood.model.User;
import com.freshfood.repository.CartRepository;
import com.freshfood.repository.UserRepository;
import com.freshfood.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    @Override
    public UserDetailsService userDetails() {
        return username -> userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public int addUser(UserRequestDTO userRequestDTO) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = User.builder()
                .username(userRequestDTO.getUsername())
                .password(encoder.encode(userRequestDTO.getPassword()))
                .email(userRequestDTO.getEmail())
                .numberPhone(userRequestDTO.getPhone())
                .build();
        userRepository.save(user).getId();
        cartRepository.save(new Cart(user, new HashSet<>()));
        return user.getId();
    }
}
