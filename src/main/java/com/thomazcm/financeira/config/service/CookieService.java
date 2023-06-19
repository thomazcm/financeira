package com.thomazcm.financeira.config.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class CookieService {
    
    @Value("${spring.profiles.active}")
    private String activeProfile;
    @Value("${rest.jwt.expiration}")
    private String expiration;

    public Cookie getCookieFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JWT-TOKEN")) {
                    return cookie;
                }
            }
        }
        return null;
    }

    public Cookie createFreshCookie(String jwtToken) {
        Cookie jwtCookie = new Cookie("JWT-TOKEN", jwtToken);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(Integer.parseInt(expiration)*3600);
        if (!"local".equals(activeProfile)) {
            jwtCookie.setSecure(true);
        }
        return jwtCookie;
    }

    
}
