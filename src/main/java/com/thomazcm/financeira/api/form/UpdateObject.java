package com.thomazcm.financeira.api.form;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.thomazcm.financeira.model.Entry;
import com.thomazcm.financeira.model.Expense;
import com.thomazcm.financeira.model.ExpenseCategory;

public class UpdateObject {
    
    
    private String name;
    private BigDecimal value;
    private LocalDate date;
    private ExpenseCategory category;
    
    
    public UpdateObject(EntryForm form) {
        this.name = form.getName();
        this.value = form.getValue();
        this.date = form.getDate();
    }
    
    public UpdateObject(ExpenseForm form) {
        this.name = form.getName();
        this.value = form.getValue();
        this.date = form.getDate();
        this.category = form.getCategory();
    }
    
    public Entry updateEntry(Entry entry) {
        entry.setName(updateName(entry));
        entry.setValue(updateValue(entry));
        entry.setDate(updateDate(entry));
        if (entry instanceof Expense) {
            entry = updateCategory(entry);
        }
        return entry;
    }

    public String updateName(Entry entry) {
        return name == null  || name.isBlank() ? entry.getName() : name;
    }
    
    public BigDecimal updateValue(Entry entry) {
        return value == null ? entry.getValue() : value;
    }
    
    public LocalDate updateDate(Entry entry) {
        return date == null  ? entry.getDate() : date;
    }
    
    public Entry updateCategory(Entry entry) {
        ((Expense) entry).setCategory(category == null ? ((Expense) entry).getCategory() : category);
        return entry;
    }
    

}
