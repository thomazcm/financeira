package com.thomazcm.financeira.api.form;

import com.thomazcm.financeira.model.Income;

public class IncomeForm extends EntryForm{
    
    public Income toIncome() {
        return new Income(this.getName(), this.getValue(), this.getDate());
    }
    
}
