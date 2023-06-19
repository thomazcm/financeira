package com.thomazcm.financeira.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.thomazcm.financeira.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long>{

}
