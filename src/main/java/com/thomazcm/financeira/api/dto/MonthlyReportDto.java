package com.thomazcm.financeira.api.dto;

import java.math.BigDecimal;
import java.util.EnumMap;
import com.thomazcm.financeira.model.ExpenseCategory;

public class MonthlyReportDto {
    
    private BigDecimal incomeTotal;
    private BigDecimal expenseTotal;
    private BigDecimal monthlyBalance;
    private EnumMap<ExpenseCategory, BigDecimal> categoryExpenses;

    public MonthlyReportDto(BigDecimal incomeSum, BigDecimal expenseSum, BigDecimal monthlyBalance,
            EnumMap<ExpenseCategory, BigDecimal> categoryExpenses) {
        
        this.incomeTotal = incomeSum;
        this.expenseTotal = expenseSum;
        this.monthlyBalance = monthlyBalance;
        this.categoryExpenses = categoryExpenses;
        
    }

    public BigDecimal getIncomeTotal() {
        return this.incomeTotal;
    }

    public BigDecimal getExpenseTotal() {
        return this.expenseTotal;
    }

    public BigDecimal getMonthlyBalance() {
        return this.monthlyBalance;
    }

    public EnumMap<ExpenseCategory, BigDecimal> getCategoryExpenses() {
        return this.categoryExpenses;
    }

}
