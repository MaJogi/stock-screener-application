package com.taltech.stockscreenerapplication.controller.rawStatement;

import com.taltech.stockscreenerapplication.model.statement.cashflow.CashflowStatRaw;
import com.taltech.stockscreenerapplication.repository.CashflowStatRawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/rawCashflowStats")
public class RawCashflowStatController {
    @Autowired
    private CashflowStatRawRepository cashflowStatRawRepository;

    @GetMapping
    public Iterable<CashflowStatRaw> getRawCashflowStatements() {
        return cashflowStatRawRepository.findAll();
    }

    @GetMapping("/{id}")
    public CashflowStatRaw getRawIncomeStatement(@PathVariable final Long id) {

        return cashflowStatRawRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unable to find company by id: " + id));
    }
}
