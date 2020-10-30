package com.virtuallab.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JsonCredentialsAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UserLogin loginRequest = parseLoginRequest(request);
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        setDetails(request, authRequest);

        return getAuthenticationManager().authenticate(authRequest);
    }

    private UserLogin parseLoginRequest(HttpServletRequest request) {
        try {
            return objectMapper.readValue(request.getInputStream(), UserLogin.class);
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }

}
