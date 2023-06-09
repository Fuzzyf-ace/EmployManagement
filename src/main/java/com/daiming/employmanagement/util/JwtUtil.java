package com.daiming.employmanagement.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;


    public String generateToken(String subject) {
        System.out.println(new Date(System.currentTimeMillis() + 7 * 60 * 60 * 24 * 1000));
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 7 * 60 * 60 * 24 * 1000))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    private Claims extractClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public String extractUsername(String token) {
        String subject = extractClaims(token).getSubject();
        //remove UserRole which has length 8
        return subject.substring(8, subject.length());

    }
    public String extractUserRole(String token) {
        return extractClaims(token).getSubject().substring(0,8);
    }

    public Date extractExpiration(String token) {
        return extractClaims(token).getExpiration();
    }

    public Boolean validateToken(String token) {
        return extractExpiration(token).after(new Date());
    }
}
