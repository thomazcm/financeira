package com.thomazcm.financeira.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.thomazcm.financeira.model.Entry;

public interface EntryRepository<T extends Entry> extends JpaRepository<T, Long> {

    List<T> findByUser_Id(Long id);

    Optional<T> findByIdAndUserId(Long id, Long userId);

    List<T> findByUser_IdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);
    
    List<T> findByUser_IdAndName(Long userId, String name);

}
