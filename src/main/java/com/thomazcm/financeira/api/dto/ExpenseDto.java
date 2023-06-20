package com.thomazcm.financeira.api.dto;

import java.util.List;
import java.util.stream.Collectors;
import com.thomazcm.financeira.model.Expense;
import com.thomazcm.financeira.model.ExpenseCategory;

public class ExpenseDto extends EntryDto{

    private ExpenseCategory category;
    
    
    public ExpenseDto(Expense expense) {
        super(expense);
        this.category = expense.getCategory();
    }

    public ExpenseCategory getCategory() {
        return this.category;
    }
    
    public static List<ExpenseDto> toList(List<Expense> expenseList) {
        return expenseList.stream().map(ExpenseDto::new).collect(Collectors.toList());
    }

}
