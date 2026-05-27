package com.athidhi.auth_service.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    /* SECURE 256-BIT KEY */

    private static final Key SECRET_KEY = Keys.hmacShaKeyFor("MySuperSecureJwtSecretKeyForAthidhiGruhaProject12345".getBytes());

    public String generateToken(String userId) {

        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ 1000 * 60 * 60))
                .signWith(SECRET_KEY,SignatureAlgorithm.HS256)
                .compact();
    }
}
