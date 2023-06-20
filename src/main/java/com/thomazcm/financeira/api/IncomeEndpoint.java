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
import com.thomazcm.financeira.api.dto.IncomeDto;
import com.thomazcm.financeira.api.form.IncomeForm;
import com.thomazcm.financeira.api.service.EntryHelper;
import com.thomazcm.financeira.api.service.EntryService;
import com.thomazcm.financeira.model.Income;
import com.thomazcm.financeira.repository.IncomeRepository;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/receitas")
public class IncomeEndpoint {

    private final EntryService<Income> service;
    private final IncomeRepository repository;
    private final EntryHelper helper;
    private final String CREATED_URI = "/receitas/{id}";

    public IncomeEndpoint(EntryService<Income> service, IncomeRepository repository,
            EntryHelper helper) {
        this.service = service;
        this.repository = repository;
        this.helper = helper;
    }

    @GetMapping
    public ResponseEntity<List<IncomeDto>> listIncome(HttpServletRequest request,
            @RequestParam(required = false) String name) {

        var incomeList = name == null ? service.findAll(request, repository)
                : service.findByName(name, request, repository);
        return ResponseEntity.ok(IncomeDto.toList(incomeList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncomeDto> findIncomeFromId(HttpServletRequest request,
            @PathVariable int id) {

        Income income = service.findById(id, request, repository);

        return income != null ? ResponseEntity.ok(new IncomeDto(income))
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/{year}/{month}")
    public ResponseEntity<List<IncomeDto>> listIncomeByMonth(HttpServletRequest request,
            @PathVariable int year, @PathVariable int month) {
        List<Income> allIncomeFromMonth = service.listByMonth(year, month, request, repository);

        return ResponseEntity.ok(IncomeDto.toList(allIncomeFromMonth));
    }

    @PostMapping
    public ResponseEntity<IncomeDto> saveNewIncome(HttpServletRequest request,
            @RequestBody IncomeForm form) {

        var income = service.saveEntry(form.toIncome(), request, repository);
        return helper.created(CREATED_URI, income.getId()).body(new IncomeDto(income));
    }


    @PutMapping("/{id}")
    public ResponseEntity<IncomeDto> updateIncome(HttpServletRequest request,
            @RequestBody IncomeForm form, @PathVariable int id) {

        Income income = service.findById(id, request, repository);
        if (income == null) {
            return ResponseEntity.notFound().build();
        }
        if (service.updateEntry(income, form.toUpdateObject(), repository)) {
            return ResponseEntity.ok(new IncomeDto(income));
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIncome(HttpServletRequest request, @PathVariable int id) {
        if (service.deleteById(id, request, repository)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }



}
