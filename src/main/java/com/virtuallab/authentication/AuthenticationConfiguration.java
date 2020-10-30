package com.virtuallab.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpMethod.GET;

@Configuration
public class AuthenticationConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private LogoutHandler logoutHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    private static final String[] AUTH_WHITELIST = {
        "/swagger-ui/**",
        "/swagger-resources/**",
        "/swagger-ui.html",
        "/v2/api-docs",
        "/webjars/**"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public HttpStatusReturningLogoutSuccessHandler successLogoutHandler() {
        return new HttpStatusReturningLogoutSuccessHandler();
    }

    @Bean
    public NoRedirectAuthenticationSuccessHandler noRedirectAuthenticationSuccessHandler() {
        return new NoRedirectAuthenticationSuccessHandler();
    }

    @Bean
    public JsonCredentialsAuthenticationFilter jsonCredentialsAuthenticationFilter() throws Exception {
        JsonCredentialsAuthenticationFilter filter = new JsonCredentialsAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(noRedirectAuthenticationSuccessHandler());
        filter.setFilterProcessesUrl("/authentication");
        filter.setUsernameParameter("username");
        filter.setPasswordParameter("password");
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .httpBasic().disable();

        http.csrf().disable();

        http
            .exceptionHandling()
            .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            .accessDeniedHandler(accessDeniedHandler());

        http.addFilter(jsonCredentialsAuthenticationFilter());

        http
            .authorizeRequests()
            .antMatchers(GET, AUTH_WHITELIST).permitAll()
            .antMatchers("/authentication").permitAll()
            .antMatchers("/**").authenticated();

        http
            .logout()
            .addLogoutHandler(logoutHandler)
            .logoutSuccessHandler(successLogoutHandler());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    private AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.getWriter().append("Access denied");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        };
    }

}
