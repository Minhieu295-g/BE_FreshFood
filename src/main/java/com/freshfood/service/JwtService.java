package com.freshfood.service;

import com.freshfood.util.TokenType;
import io.jsonwebtoken.Jwt;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(UserDetails userDetails);
    String generateRefreshToken(UserDetails userDetails);
    String extractUsername(String token, TokenType tokenType);
    boolean isValid(String token, TokenType tokenType, UserDetails userDetails);

}
