package com.tli.auth.domain.helpers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtHelper {
    @Value("${application.jwt.secret}")
    private String secretKey;
    public String createToken(String email){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 1000 * 60 * 60 * 24);
        return Jwts.builder()
                .setSubject(email)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getPrivateKey())
                .compact();
    }

    private SecretKey getPrivateKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}
