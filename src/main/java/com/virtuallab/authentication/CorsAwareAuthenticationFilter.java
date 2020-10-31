package com.virtuallab.authentication;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@Component
@Order(HIGHEST_PRECEDENCE)
public class CorsAwareAuthenticationFilter extends OncePerRequestFilter {

    private final Set<String> allowedOrigins = new HashSet<String>() {
        {
            add("http://localhost:4200");
            add("http://virtlab.eastus.cloudapp.azure.com");
        }
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String originHeader = request.getHeader("Origin");

        if (allowedOrigins.contains(originHeader)) {
            response.setHeader("Access-Control-Allow-Origin", originHeader);
            response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods", "POST, PUT, PATCH, GET, OPTIONS, DELETE, HEAD");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader(
                "Access-Control-Expose-Headers",
                "x-auth-token, x-stateless-token, x-total-items, x-is-batch"
            );
        }

        // return 200 to any OPTIONS request
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(200);
        } else {
            filterChain.doFilter(request, response);
        }
    }

}
