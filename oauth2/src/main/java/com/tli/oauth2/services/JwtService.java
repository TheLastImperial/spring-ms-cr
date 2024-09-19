package com.tli.oauth2.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class JwtService {

    @Value("${application.jwt.secret.key}")
    private String SECRET_KEY;

    public boolean validate(String token) {
        try{
            Jwts.parser()
                    .verifyWith(getSignKey())
                    .build()
                    .parse(token);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public String generateToken(Map<String, Object> claims, String sub) {
        return createToken(claims, sub);
    }

    private String createToken(Map<String, Object> claims, String sub) {
        return Jwts.builder()
                .claims(claims)
                .subject(sub)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignKey())
                .compact();
    }

    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
