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
import com.thomazcm.financeira.config.filters.AuthenticationViaTokenFilter;
import com.thomazcm.financeira.config.service.AuthenticationService;
import com.thomazcm.financeira.config.service.CookieService;
import com.thomazcm.financeira.config.service.JwtService;

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
        http.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/user/**", "/auth/**", "/logout/**", "/js/**", "/css/**")
                .permitAll()).authorizeHttpRequests(authorize -> {
                    authorize.anyRequest().authenticated();
                }).csrf().disable()
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
