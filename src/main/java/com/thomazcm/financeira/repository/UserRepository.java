package com.thomazcm.financeira.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.thomazcm.financeira.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String username);

}
