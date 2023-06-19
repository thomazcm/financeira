package com.thomazcm.financeira.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class User implements UserDetails, Serializable {

    private static final long serialVersionUID = -7177753882473387404L;

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Entry> entries = new ArrayList<>();
    
    @Column(unique = true)
    private String email;
    
    @JsonIgnore
    private String password;
    
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Profile> profiles = new HashSet<>();
    
    
    
    public User() {}
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
    
    public void addProfile(Profile profile) {
        profiles.add(profile);
        profile.addUser(this);
    }
 

    public List<Entry> getEntries() {
        return List.copyOf(entries);
    }
    
    public void addEntry(Entry entry) {
        this.entries.add(entry);
        entry.setUser(this);
    }
    
    public Long getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public Collection<Profile> getProfiles() {
        return List.copyOf(profiles);
    }
    

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return profiles;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
