package com.thomazcm.financeira.api.dto;

import com.thomazcm.financeira.model.Expense;

public class ExpenseDto extends EntryDto{

    public ExpenseDto(Expense expense) {
        super(expense);
    }

}
