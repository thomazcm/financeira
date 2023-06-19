package com.thomazcm.financeira.model;

import jakarta.persistence.Entity;

@Entity
public class Expense extends Entry {
    
    private ExpenseCategory category;

    public ExpenseCategory getCategory() {
        return this.category;
    }

    public void setCategory(ExpenseCategory category) {
        this.category = category;
    }
    
}
