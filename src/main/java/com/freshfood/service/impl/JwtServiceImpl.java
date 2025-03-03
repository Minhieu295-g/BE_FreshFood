package com.freshfood.service.impl;

import com.freshfood.service.JwtService;
import com.freshfood.util.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import static com.freshfood.util.TokenType.ACCESS_TOKEN;
import static com.freshfood.util.TokenType.REFRESH_TOKEN;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${spring.jwt.expireHour}")
    private long expireHour;
    @Value("${spring.jwt.expireDay}")
    private long expireDay;
    @Value("${spring.jwt.accessKey}")
    private String accessKey;
    @Value("${spring.jwt.refeshKey}")
    private String refreshKey;

    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(Map.of("userId", userDetails.getAuthorities()), userDetails);
    }

    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        return generateToken(Map.of("userId", userDetails.getAuthorities()), userDetails);
    }

    @Override
    public String extractUsername(String token, TokenType tokenType) {
        return extractClaim(token, tokenType, Claims::getSubject);
    }

    @Override
    public boolean isValid(String token, TokenType tokenType, UserDetails userDetails) {
        String username = extractUsername(token, tokenType);
        return (username.equals(userDetails.getUsername())) && !isValid(token, tokenType, userDetails);
    }

    public String generateToken(Map<String, Object> claims, UserDetails userDetails){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireHour * 60 * 60 * 1000))
                .signWith(getKey(ACCESS_TOKEN), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(Map<String, Object> claims, UserDetails userDetails){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireDay * 24 * 60 * 60 * 1000))
                .signWith(getKey(REFRESH_TOKEN), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey(TokenType tokenType){
        if(ACCESS_TOKEN.equals(tokenType)){
            return Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessKey));
        }else {
            return Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshKey));
        }
    }
    public <T> T extractClaim(String token, TokenType tokenType, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaim(token, tokenType);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaim(String token, TokenType tokenType) {
        return Jwts.parserBuilder().setSigningKey(getKey(tokenType)).build().parseClaimsJws(token).getBody();
    }
    private boolean isTokenExpired(String token, TokenType tokenType) {
        return extractExpiration(token, tokenType).before(new Date());
    }
    private Date extractExpiration(String token, TokenType tokenType) {
        return extractClaim(token, tokenType, Claims::getExpiration);
    }
}
