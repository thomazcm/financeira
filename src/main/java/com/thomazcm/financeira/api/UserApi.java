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
import com.thomazcm.financeira.model.Profile;
import com.thomazcm.financeira.model.User;
import com.thomazcm.financeira.repository.ProfileRepository;
import com.thomazcm.financeira.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserApi {
    
    private UserRepository userRepository;
    private ProfileRepository profileRepository;
    
    public UserApi(UserRepository userRepository, ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }

    @PostMapping
    public ResponseEntity<UserDto> newUser (@RequestBody UserForm form, BCryptPasswordEncoder encoder) {
        User user = form.toUser();
        user.setPassword(encoder.encode(user.getPassword()));
        Profile profile = profileRepository.findById(1L).orElse(null);
        user.addProfile(profile);
        
        profileRepository.save(profile);
        userRepository.save(user);
        return ResponseEntity
                .created(UriComponentsBuilder
                        .fromPath("/user/{id}").buildAndExpand(user.getId()).toUri())
                .body(new UserDto(user));
    }
    
}
