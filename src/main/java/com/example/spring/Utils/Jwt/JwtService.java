package com.example.spring.Jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtService {


    private static String SECRET_KEY = "45774a4a9b3ab452ea91681d20a1ace04168ebde939d88e7ee647eeacf9d487a";


    public String getToken(String email) {
        return getToken(new HashMap<>(), email);
    }

    public String getToken(HashMap<String, Object> extraClaims, String email) {
        return "Bearer " + Jwts.builder()
                .subject(email)
                .claims(extraClaims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims allClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractSubject(String token) {
        return allClaims(token).getSubject();
    }


    public boolean tokenIsValid(String email, String token) {
        String subject = extractSubject(token);
        return (email.equals(subject) && tokenExpired(token));
    }

    private boolean tokenExpired(String token) {
        return !allClaims(token).getExpiration().before(new Date());
    }


    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
