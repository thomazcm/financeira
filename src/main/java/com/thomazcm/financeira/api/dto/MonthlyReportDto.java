package com.thomazcm.financeira.api.dto;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;
import com.thomazcm.financeira.model.ExpenseCategory;

public class MonthlyReportDto {
    
    private Double incomeTotal;
    private Double expenseTotal;
    private Double monthlyBalance;
    private EnumMap<ExpenseCategory, Double> categoryExpenses;

    public MonthlyReportDto(BigDecimal incomeSum, BigDecimal expenseSum,
            EnumMap<ExpenseCategory, BigDecimal> categoryExpenses) {
        
        this.incomeTotal = incomeSum.doubleValue();
        this.expenseTotal = expenseSum.doubleValue();
        this.monthlyBalance = incomeSum.subtract(expenseSum).doubleValue();
        this.categoryExpenses = mapToDouble(categoryExpenses);
        
    }

    private EnumMap<ExpenseCategory, Double> mapToDouble(
            EnumMap<ExpenseCategory, BigDecimal> categoryExpenses) {
        EnumMap<ExpenseCategory, Double> doubleMap = new EnumMap<>(ExpenseCategory.class);
        for (Map.Entry<ExpenseCategory, BigDecimal> entry : categoryExpenses.entrySet()) {
            doubleMap.put(entry.getKey(), entry.getValue().doubleValue());
        }
        return doubleMap;
    }

    public Double getIncomeTotal() {
        return this.incomeTotal;
    }

    public Double getExpenseTotal() {
        return this.expenseTotal;
    }

    public Double getMonthlyBalance() {
        return this.monthlyBalance;
    }

    public EnumMap<ExpenseCategory, Double> getCategoryExpenses() {
        return this.categoryExpenses;
    }
    
    

}
