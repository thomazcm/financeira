package com.thomazcm.financeira.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.persistence.Entity;

@Entity
public class Expense extends Entry {
    
    private ExpenseCategory category;
    
    public Expense() {}
    public Expense(String name, BigDecimal value, LocalDate date) {
        super(name, value, date);
    }

    public ExpenseCategory getCategory() {
        return this.category;
    }

    public void setCategory(ExpenseCategory category) {
        this.category = category;
    }
    
}
