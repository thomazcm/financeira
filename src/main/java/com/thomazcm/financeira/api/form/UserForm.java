package com.thomazcm.financeira.api.form;

import com.thomazcm.financeira.model.User;

public class UserForm {

    private String email;
    private String password;
    
    public String getEmail() {
        return this.email;
    }
    public String getPassword() {
        return this.password;
    }
    public User toUser() {
        return new User(email, password);
    }
    
}
