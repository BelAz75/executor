package com.virtuallab.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtuallab.user.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class NoRedirectAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    {
        setRedirectStrategy(new NoRedirectStrategy());
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        UserAuthenticationResponse authResponse = new UserAuthenticationResponse(
            userEntity.getId(),
            userEntity.getFirstName(),
            userEntity.getLastName(),
            userEntity.getEmail(),
            userEntity.getUsername(),
            userEntity.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())
        );
        response.getWriter().write(objectMapper.writeValueAsString(authResponse));
        response.setStatus(HttpStatus.OK.value());
    }

    private static class NoRedirectStrategy implements RedirectStrategy {
        @Override
        public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {

        }
    }

}
