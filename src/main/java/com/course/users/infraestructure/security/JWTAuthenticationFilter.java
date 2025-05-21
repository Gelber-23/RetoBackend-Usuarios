package com.course.users.infraestructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        AuthCredentials authCredentials= new AuthCredentials();

        try {
            authCredentials = new ObjectMapper().readValue(request.getReader(), AuthCredentials.class);
        }catch (IOException e){
            //nothing
        }

        UsernamePasswordAuthenticationToken usernamePAT = new UsernamePasswordAuthenticationToken(
                authCredentials.getEmail(),
                authCredentials.getPassword(),
                Collections.emptyList()
        );

        return getAuthenticationManager().authenticate(usernamePAT);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        UserDetailsImpl userDetails= (UserDetailsImpl) authResult.getPrincipal();

        Object[] authorities = userDetails.getAuthorities().toArray();
        String token = TokenUtils.createToken(userDetails.getName(), userDetails.getUsername(),
                authorities[0].toString(), userDetails.getId());
        response.addHeader("Authorization", "Bearer "+token);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");


        String responseBody = new ObjectMapper().writeValueAsString(Collections.singletonMap("token", token));
        response.getWriter().write(responseBody);
        response.getWriter().flush();



        super.successfulAuthentication(request, response, chain, authResult);
    }
}
