package com.thomazcm.financeira.api.form;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class LoginForm {

    private String email;
    private String password;

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public UsernamePasswordAuthenticationToken toUserPassToken() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }

}
