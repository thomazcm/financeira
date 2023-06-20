package com.thomazcm.financeira.api;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.thomazcm.financeira.api.dto.MonthlyReportDto;
import com.thomazcm.financeira.api.service.EntryService;
import com.thomazcm.financeira.api.service.ReportHelper;
import com.thomazcm.financeira.model.Expense;
import com.thomazcm.financeira.model.Income;
import com.thomazcm.financeira.repository.ExpenseRepository;
import com.thomazcm.financeira.repository.IncomeRepository;
import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/report")
public class MonthlyReportEndpoint {

    private final EntryService<Income> incomeService;
    private final IncomeRepository incomeRepository;
    private final EntryService<Expense> expenseService;
    private final ExpenseRepository expenseRepository;
    private final ReportHelper helper;


    public MonthlyReportEndpoint(EntryService<Income> incomeService,
            EntryService<Expense> expenseService, IncomeRepository incomeRepository, ReportHelper helper) {
        this.incomeService = incomeService;
        this.expenseService = expenseService;
        this.incomeRepository = incomeRepository;
        this.expenseRepository = null;
        this.helper = helper;
    }

    @GetMapping("/{year}/{month}")
    public ResponseEntity<MonthlyReportDto> getMonthlyReport(HttpServletRequest request,
            @PathVariable int year, @PathVariable int month) {
        List<Income> monthlyIncome = incomeService.listByMonth(year, month, request, incomeRepository);
        List<Expense> monthlyExpenses = expenseService.listByMonth(year, month, request, expenseRepository);
        MonthlyReportDto report = helper.getMonthlyReport(monthlyIncome, monthlyExpenses);
        return ResponseEntity.ok(report);
    }



}
