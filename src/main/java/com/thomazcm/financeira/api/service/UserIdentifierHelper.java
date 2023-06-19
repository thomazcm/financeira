package com.thomazcm.financeira.api.service;

import org.springframework.stereotype.Service;
import com.thomazcm.financeira.config.service.CookieService;
import com.thomazcm.financeira.config.service.JwtService;
import com.thomazcm.financeira.model.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserIdentifierHelper {
    
    private JwtService jwtService;
    private CookieService cookieService;
    
    public UserIdentifierHelper(JwtService jwtService, CookieService cookieService) {
        this.jwtService = jwtService;
        this.cookieService = cookieService;
    }

    public User getUserFromRequest(HttpServletRequest request) {
        String jwtToken = getTokenFromRequest(request);
        return jwtService.getUserFromToken(jwtToken);
        
    }

    public Long getUserIdFromRequest(HttpServletRequest request) {
        String jwtToken = getTokenFromRequest(request);
        return jwtService.getIdFromToken(jwtToken);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        Cookie cookie = cookieService.getCookieFromRequest(request);
        return cookie == null ? null : cookie.getValue();
    }

}
