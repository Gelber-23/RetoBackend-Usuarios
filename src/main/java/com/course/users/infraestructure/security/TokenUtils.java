package com.course.users.infraestructure.security;

import com.course.users.domain.exeption.JwtNotCreate;
import com.course.users.domain.utils.constants.ValuesConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;


public class TokenUtils {

    private static final String ACCESS_TOKEN_SECRET= ValuesConstants.JWT_SECRET_KEY;
    private static final Long ACCESS_TOKEN_VALIDITY_SECONDS = ValuesConstants.ACCESS_TOKEN_VALIDITY_SECONDS;


    public static String createToken(String name, String email, String role , Long id) {
        long expirationTime = ACCESS_TOKEN_VALIDITY_SECONDS * 1_000;
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        Map<String, Object> extra = new HashMap<>();
        extra.put("name", name);
        extra.put("role", role);
        extra.put("id", id);

        return Jwts.builder()
                .setSubject(email)
                .setExpiration(expirationDate)
                .addClaims(extra)
                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
                .compact();
    }

    public static UsernamePasswordAuthenticationToken getAuthentication(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            Long id = claims.get("id", Long.class);
            String role = claims.get("role", String.class);
            List<GrantedAuthority> authorities = List.of(
                    new SimpleGrantedAuthority(role)
            );
            return new UsernamePasswordAuthenticationToken(id, null, authorities);

        } catch (JwtException e) {
            throw new JwtNotCreate(e.getCause().toString());
        }
    }




    public static String getEmail(String token){
        try {
            Claims claims  = Jwts.parserBuilder()
                    .setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return  claims.getSubject();

        }catch (JwtException e){
            throw new JwtNotCreate(e.getCause().toString());
        }
    }

}
