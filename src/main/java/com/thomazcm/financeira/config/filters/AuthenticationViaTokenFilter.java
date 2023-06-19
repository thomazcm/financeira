package com.thomazcm.financeira.config.filters;

import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import com.thomazcm.financeira.config.service.CookieService;
import com.thomazcm.financeira.config.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationViaTokenFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private CookieService cookieService;

    public AuthenticationViaTokenFilter(JwtService jwtService, CookieService cookieService) {
        this.jwtService = jwtService;
        this.cookieService = cookieService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        Cookie requestCookie = cookieService.getCookieFromRequest(request);
        String jwtToken = requestCookie == null ? null : requestCookie.getValue();

        if (jwtToken != null && jwtService.isTokenValid(jwtToken)) {
            if (jwtService.isTokenExpiring(jwtToken)) {
                jwtToken = refreshToken(jwtToken, response);
            }
            authenticateRequest(jwtToken);
        }

        filterChain.doFilter(request, response);
    }

    private String refreshToken(String jwtToken, HttpServletResponse response) {
        Cookie freshCookie = cookieService.createFreshCookie(jwtToken);
        response.addCookie(freshCookie);
        return freshCookie.getValue();
    }

    private void authenticateRequest(String jwtToken) {
        var user = jwtService.getUserFromToken(jwtToken);
        var upaToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(upaToken);
    }



}
