package com.thomazcm.financeira.model;

import java.io.Serializable;
import org.springframework.security.core.GrantedAuthority;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Profile implements GrantedAuthority, Serializable {

    private static final long serialVersionUID = -8910423939093339999L;

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    
    @Override
    public String getAuthority() {
        return name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }
    

}
