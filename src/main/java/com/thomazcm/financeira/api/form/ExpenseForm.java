package com.thomazcm.financeira.api.form;

import com.thomazcm.financeira.model.Expense;
import com.thomazcm.financeira.model.ExpenseCategory;

public class ExpenseForm extends EntryForm {
    
    private ExpenseCategory category;
    
    public ExpenseCategory getCategory() {
        return this.category;
    }
    
    public Expense toExpense() {
        var expense = new Expense(getName(), getValue(), getDate());
        expense.setCategory(category == null ? ExpenseCategory.OUTRAS : category);
        return expense;
    }
    
    @Override
    public UpdateObject toUpdateObject() {
        return new UpdateObject(this.getName(), this.getValue(), this.getDate(), this.getCategory());
    }

}
