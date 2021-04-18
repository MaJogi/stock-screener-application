package com.taltech.stockscreenerapplication.controller;

import com.taltech.stockscreenerapplication.model.statement.incomestatement.IncomeStatRaw;
import com.taltech.stockscreenerapplication.repository.IncomeStatRawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/rawIncomeStats")
public class RawIncomeStatController {
    @Autowired
    private IncomeStatRawRepository incomeStatRawRepository;

    @GetMapping
    public Iterable<IncomeStatRaw> getRawIncomeStatements() {

        return incomeStatRawRepository.findAll();
    }

    @GetMapping("/{id}")
    public IncomeStatRaw getRawIncomeStatement(@PathVariable final Long id) {

        return incomeStatRawRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find company by id: " + id));
    }
}
