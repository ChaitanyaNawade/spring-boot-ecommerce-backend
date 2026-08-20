package com.chaitanya.jayganesh.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService
{
    @Value("${jwt.secret}")
    private  String secretKey;

    @Value("${jwt.expiration}")
    private  Long expirationTime;


    private SecretKey getSignKey()
    {
        byte []keyBytes = secretKey.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public String generateToken(String email)
    {
       return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+expirationTime)).
                signWith(getSignKey())
                .compact();
    }

    public String extractEmail(String token)
    {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    private boolean isTokenExpired(String token)
    {
        Date expiration = Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();

                return expiration.before(new Date());
    }


    public boolean isTokenValid(String token,String email)
    {
        return extractEmail(token).equals(email) && !isTokenExpired(token);
    }

}
