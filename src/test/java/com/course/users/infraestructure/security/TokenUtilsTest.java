package com.course.users.infraestructure.security;

import com.course.users.domain.exeption.JwtNotCreate;
import com.course.users.domain.utils.constants.ValuesConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TokenUtilsTest {
    private static final String SECRET = ValuesConstants.JWT_SECRET_KEY;

    @Test
    void createToken_embedsSubjectAndClaims() {
        String name = "Alice";
        String email = "alice@example.com";
        String role = "ROLE_USER";
        Long id = 1L;

        String token = TokenUtils.createToken(name, email, role, id);
        assertNotNull(token);

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token)
                .getBody();

        assertEquals(email, claims.getSubject());
        assertEquals(name, claims.get("name", String.class));
        assertEquals(role, claims.get("role", String.class));
        assertEquals(id, claims.get("id", Long.class));
        assertTrue(claims.getExpiration().after(new Date()));
    }

    @Test
    void getAuthentication_withValidToken_returnsAuthToken() {
        String token = TokenUtils.createToken("Bob", "bob@example.com", "ROLE_ADMIN", 2L);
        UsernamePasswordAuthenticationToken auth = TokenUtils.getAuthentication(token);

        assertEquals(2L, auth.getPrincipal());
        assertNull(auth.getCredentials());
        List<GrantedAuthority> auths = (List<GrantedAuthority>) auth.getAuthorities();
        assertEquals(1, auths.size());
        assertEquals("ROLE_ADMIN", auths.get(0).getAuthority());
    }

    @Test
    void getAuthentication_withInvalidToken_throwsJwtNotCreate() {
        assertThrows(JwtNotCreate.class, () -> TokenUtils.getAuthentication("invalid.token.here"));
    }

    @Test
    void getEmail_withValidToken_returnsEmail() {
        String email = "carol@example.com";
        String token = TokenUtils.createToken("Carol", email, "ROLE_USER", 3L);
        assertEquals(email, TokenUtils.getEmail(token));
    }

    @Test
    void getEmail_withInvalidToken_throwsJwtNotCreate() {
        assertThrows(JwtNotCreate.class, () -> TokenUtils.getEmail("not.a.jwt"));
    }

}