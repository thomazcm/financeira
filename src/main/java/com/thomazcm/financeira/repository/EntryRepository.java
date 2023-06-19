package com.thomazcm.financeira.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.thomazcm.financeira.model.Entry;

public interface EntryRepository<T extends Entry> extends JpaRepository<T, Long>{

    List<T> findByUser_Id(Long id);
    
}
