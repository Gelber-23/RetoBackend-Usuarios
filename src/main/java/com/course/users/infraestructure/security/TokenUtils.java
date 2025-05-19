package com.course.users.infraestructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "jwt.access-token")
public class TokenUtils {

    private static String secret;
    private static Long validitySeconds;
    
    
    public static String createToken(String name, String email, String role , Long id) {
        long expirationTime = validitySeconds * 1_000;
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        Map<String, Object> extra = new HashMap<>();
        extra.put("name", name);
        extra.put("role", role);
        extra.put("id", id);

        return Jwts.builder()
                .setSubject(email)
                .setExpiration(expirationDate)
                .addClaims(extra)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public static UsernamePasswordAuthenticationToken getAuthentication(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secret.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            String email = claims.getSubject();
            return new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());

        } catch (JwtException e) {
            return null;
        }
    }




    public static String getEmail(String token){
        try {
            Claims claims  = Jwts.parserBuilder()
                    .setSigningKey(secret.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return  claims.getSubject();

        }catch (JwtException e){
            return  null;
        }
    }

    public static Long getAuthenticatedUserId(String token){
        try {
            Claims claims  = Jwts.parserBuilder()
                    .setSigningKey(secret.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.get("id", Long.class);

        }catch (JwtException e){
            return  null;
        }
    }

}
