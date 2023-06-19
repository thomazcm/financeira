package com.thomazcm.financeira.config.service;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.thomazcm.financeira.model.User;
import com.thomazcm.financeira.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    @Value("${rest.jwt.expiration}")
    private String expiration;
    @Value("${rest.jwt.secret}")
    private String secret;
    private UserRepository repository;

    public JwtService(UserRepository repository) {
        this.repository = repository;
    }

    public boolean isTokenValid(String jwtToken) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(jwtToken);
            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException
                | SignatureException | IllegalArgumentException e) {
            logger.error("JWT validation failed", e);
            return false;
        }
    }

    public User getUserFromToken(String jwtToken) {
        Long id = Long.parseLong(getClaims(jwtToken).getSubject());
        return repository.findById(id).orElse(null);
    }

    public boolean isTokenExpiring(String jwtToken) {
        Date jwtExpirationDate = getClaims(jwtToken).getExpiration();
        long timeLeftInMilliseconds = jwtExpirationDate.getTime() - new Date().getTime();
        long minutesLeft = TimeUnit.MILLISECONDS.toMinutes(timeLeftInMilliseconds);
        return minutesLeft < Long.parseLong(expiration) / 2;
    }

    public String createToken(User user) {
        Date today = new Date();
        Date tokenExpiration = new Date(today.getTime() + Long.parseLong(expiration) * 60 * 1000);
        String jwtToken = Jwts.builder().setIssuer("financeira").setSubject(user.getId().toString())
                .setIssuedAt(today).setExpiration(tokenExpiration)
                .signWith(SignatureAlgorithm.HS256, secret).compact();
        return jwtToken;
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

}
