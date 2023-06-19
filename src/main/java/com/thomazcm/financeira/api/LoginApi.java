package com.thomazcm.financeira.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.thomazcm.financeira.api.form.LoginForm;
import com.thomazcm.financeira.config.CookieService;
import com.thomazcm.financeira.config.JwtService;
import com.thomazcm.financeira.model.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class LoginApi {

    private static final Logger logger = LoggerFactory.getLogger(LoginApi.class);

    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private CookieService cookieService;

    public LoginApi(AuthenticationManager authenticationManager, JwtService jwtService,
            CookieService cookieService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.cookieService = cookieService;
    }

    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody LoginForm form,
            HttpServletResponse response) {
        UsernamePasswordAuthenticationToken authenticationToken = form.toUserPassToken();

        try {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            User user = (User) authentication.getPrincipal();
            addCookieWithTokenToResponse(user, response);
            return ResponseEntity.ok(user);
        } catch (AuthenticationException | ClassCastException e) {
            logger.error("authentication failed for user " + form.getEmail()
            + System.lineSeparator() + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    private void addCookieWithTokenToResponse(User user, HttpServletResponse response) {
        String jwtToken = jwtService.createToken(user);
        Cookie freshCookie = cookieService.createFreshCookie(jwtToken);
        response.addCookie(freshCookie);
    }



}
