package com.thomazcm.financeira.api;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.thomazcm.financeira.api.dto.ExpenseDto;
import com.thomazcm.financeira.api.form.ExpenseForm;
import com.thomazcm.financeira.api.form.UpdateObject;
import com.thomazcm.financeira.api.service.EntryEndpointHelper;
import com.thomazcm.financeira.api.service.EntryService;
import com.thomazcm.financeira.model.Expense;
import com.thomazcm.financeira.repository.ExpenseRepository;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/despesas")
public class ExpenseEndpoint {

    private final EntryService<Expense> service;
    private final ExpenseRepository repository;
    private final EntryEndpointHelper helper;
    private final String CREATED_URI = "/despesas/{id}";

    public ExpenseEndpoint(EntryService<Expense> service, ExpenseRepository repository,
            EntryEndpointHelper helper) {
        this.service = service;
        this.repository = repository;
        this.helper = helper;
    }

    @GetMapping
    public ResponseEntity<List<ExpenseDto>> listExpense(HttpServletRequest request,
            @RequestParam(required = false) String name) {

        var expenseList = name == null ? service.findAllEntries(request, repository)
                : service.findEntryByName(name, request, repository);

        return ResponseEntity.ok(ExpenseDto.toList(expenseList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDto> findExpenseFromId(HttpServletRequest request,
            @PathVariable Long id) {
        var expense = service.findEntryById(id, request, repository);
        return expense == null ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(new ExpenseDto(expense));
    }

    @GetMapping("/{year}/{month}")
    public ResponseEntity<List<ExpenseDto>> listExpenseByMonth(HttpServletRequest request,
            @PathVariable int year, @PathVariable int month) {

        List<Expense> allExpenseFromMonth = service.listEntriesFromMonth(year, month, request, repository);
        return ResponseEntity.ok(ExpenseDto.toList(allExpenseFromMonth));
    }

    @PostMapping
    public ResponseEntity<ExpenseDto> saveNewExpense(HttpServletRequest request,
            @RequestBody ExpenseForm form) {

        var expense = service.saveEntry(form.toExpense(), request, repository);
        return helper.created(CREATED_URI, expense.getId()).body(new ExpenseDto(expense));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseDto> updateExpense(HttpServletRequest request,
            @RequestBody ExpenseForm form, @PathVariable Long id) {

        var expense = service.findEntryById(id, request, repository);
        if (expense == null) {
            return ResponseEntity.notFound().build();
        }
        if (service.updateEntry(expense, new UpdateObject(form), repository)) {
            return ResponseEntity.ok(new ExpenseDto(expense));
        }
        return ResponseEntity.badRequest().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpense(HttpServletRequest request, @PathVariable Long id) {
        if (service.deleteEntryById(id, request, repository)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
