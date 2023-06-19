package com.thomazcm.financeira.api.dto;

import java.util.List;
import java.util.stream.Collectors;
import com.thomazcm.financeira.model.Income;

public class IncomeDto extends EntryDto{

    public IncomeDto(Income income) {
        super(income);
    }
    
    public static List<IncomeDto> toList(List<Income> incomeList) {
        return incomeList.stream().map(IncomeDto::new).collect(Collectors.toList());
    }
    
}
