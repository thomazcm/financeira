package com.thomazcm.financeira.api.service;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.List;
import org.springframework.stereotype.Service;
import com.thomazcm.financeira.api.dto.MonthlyReportDto;
import com.thomazcm.financeira.model.Expense;
import com.thomazcm.financeira.model.ExpenseCategory;
import com.thomazcm.financeira.model.Income;

@Service
public class ReportHelper {

    public MonthlyReportDto getMonthlyReport(List<Income> monthlyIncome,
            List<Expense> monthlyExpense) {

        BigDecimal incomeSum = monthlyIncome.stream().map(income -> income.getValue())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal expenseSum = monthlyExpense.stream().map(expense -> expense.getValue())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal monthlyBalance = incomeSum.subtract(expenseSum);

        return new MonthlyReportDto(incomeSum, expenseSum, monthlyBalance,
                getCategoryExpenses(monthlyExpense));
    }

    private EnumMap<ExpenseCategory, BigDecimal> getCategoryExpenses(List<Expense> expenseList) {

        var categoryExpenses = new EnumMap<ExpenseCategory, BigDecimal>(ExpenseCategory.class);

        expenseList.forEach((expense -> categoryExpenses.compute(expense.getCategory(),
                (category, categoryValue) -> categoryValue == null ? expense.getValue()
                        : categoryValue.add(expense.getValue()))));
        return categoryExpenses;
    }

}
