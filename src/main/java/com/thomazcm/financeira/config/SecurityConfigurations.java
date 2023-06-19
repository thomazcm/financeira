package com.thomazcm.financeira.config;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    private AuthenticationService authenticationService;
    private JwtService jwtService;
    private CookieService cookieService;

    public SecurityConfigurations(AuthenticationService authenticationService,
            CookieService cookieService, JwtService jwtService) {
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
        this.cookieService = cookieService;
    }

    @SuppressWarnings("removal")
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorize) -> authorize.requestMatchers("/hello/**", "/user/**",
                "/auth/**", "/logout/**", "/js/**", "/css/**").permitAll()).csrf().disable()
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                                .invalidSessionUrl("/login"))
                .httpBasic(withDefaults())
                .formLogin(form -> form.defaultSuccessUrl("/hello", true).failureUrl("/login")
                        .permitAll())
                .addFilterBefore(new AuthenticationViaTokenFilter(jwtService, cookieService),
                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    public void configureGlobal(AuthenticationManagerBuilder auth, BCryptPasswordEncoder encoder)
            throws Exception {
        auth.userDetailsService(authenticationService).passwordEncoder(encoder);
    }


}
