package com.thomazcm.financeira.repository;

import java.util.List;
import com.thomazcm.financeira.model.Income;

public interface IncomeRepository extends EntryRepository<Income>  {

    List<Income> findByUser_Id(Long id);
    
}
