package com.thomazcm.financeira.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import com.thomazcm.financeira.api.dto.UserDto;
import com.thomazcm.financeira.api.form.UserForm;
import com.thomazcm.financeira.model.User;
import com.thomazcm.financeira.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserApi {
    
    private UserRepository repository;

    public UserApi(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<UserDto> newUser (@RequestBody UserForm form, BCryptPasswordEncoder encoder) {
        User user = form.toUser();
        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);
        return ResponseEntity
                .created(UriComponentsBuilder
                        .fromPath("/user/{id}").buildAndExpand(user.getId()).toUri())
                .body(new UserDto(user));
    }
    
}
