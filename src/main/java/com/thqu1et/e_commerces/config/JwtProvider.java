package com.thqu1et.e_commerces.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtProvider {

    // SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
    private SecretKey key;

    JwtProvider() {
        try {
            key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public String generateToken(Authentication auth){
        String jwt = Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+84600000))
                .claim("email",auth.getName())
                .signWith(key).compact();

        return jwt;
    }

    public String getEmailFromToken(String jwt){
        jwt = jwt.substring(7);

        Claims claims =Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

        String email = String.valueOf(claims.get("email"));
        return email;
    }
}
