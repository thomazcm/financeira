package com.thomazcm.financeira.api.dto;

import com.thomazcm.financeira.model.User;

public class UserDto {

    private String email;
    
    public UserDto(User user) {
        this.email = user.getEmail();
    }
    
    public String getEmail() {
        return this.email;
    }

}
