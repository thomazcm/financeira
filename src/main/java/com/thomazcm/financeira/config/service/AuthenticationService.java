package com.thomazcm.financeira.config.service;

import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.thomazcm.financeira.model.User;
import com.thomazcm.financeira.repository.UserRepository;

@Service
public class AuthenticationService implements UserDetailsService{
    
    private UserRepository repository;
    public AuthenticationService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> usuario = repository.findByEmail(username);
        if (usuario.isPresent()) {
            return usuario.get();
        }
        throw new UsernameNotFoundException("Dados inválidos!");
    }

}