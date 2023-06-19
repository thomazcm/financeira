package com.thomazcm.financeira.api;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
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
    public ResponseEntity<IncomeDto> saveNewIncome(HttpServletRequest request, @RequestBody IncomeForm form) {
        
        var income = service.saveEntry(form.toIncome(), request, repository);
        URI uri = UriComponentsBuilder.fromPath("/receitas/{id}").buildAndExpand(income.getId()).toUri();
        return ResponseEntity.created(uri).body(new IncomeDto(income));
    }
}
