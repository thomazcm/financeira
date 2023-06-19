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
import org.springframework.web.bind.annotation.RestController;
import com.thomazcm.financeira.api.dto.IncomeDto;
import com.thomazcm.financeira.api.form.IncomeForm;
import com.thomazcm.financeira.api.service.EntryService;
import com.thomazcm.financeira.model.Income;
import com.thomazcm.financeira.repository.IncomeRepository;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/receitas")
public class IncomeEndpoint {

    private final EntryService<Income> service;
    private final IncomeRepository repository;
    private final String CREATED_URI = "/receitas/{id}";

    public IncomeEndpoint(EntryService<Income> service, IncomeRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<IncomeDto>> listAllIncome(HttpServletRequest request) {

        var incomeList = service.findAllFromUser(request, repository);
        return ResponseEntity.ok(IncomeDto.toList(incomeList));
    }

    @PostMapping
    public ResponseEntity<IncomeDto> saveNewIncome(HttpServletRequest request,
            @RequestBody IncomeForm form) {

        var income = service.saveEntry(form.toIncome(), request, repository);
        return service.created(CREATED_URI, income.getId()).body(new IncomeDto(income));
    }


    @PutMapping("/${id}")
    public ResponseEntity<IncomeDto> updateIncome(HttpServletRequest request,
            @RequestBody IncomeForm form, @PathVariable int id) {

        Income income = service.findById(id, request, repository);
        if (income == null) {
            return ResponseEntity.notFound().build();
        }
        income = service.updateEntry(income, form.toIncome(), repository);
        return ResponseEntity.ok(new IncomeDto(income));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIncome(HttpServletRequest request, @PathVariable int id) {
        if (service.deleteById(id, request, repository)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }



}
