package com.taltech.stockscreenerapplication.controller;

import com.taltech.stockscreenerapplication.model.statement.balancestatement.BalanceStatRaw;
import com.taltech.stockscreenerapplication.repository.BalanceStatRawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/rawBalanceSheets")
public class RawBalanceSheetController {
    @Autowired
    private BalanceStatRawRepository balanceStatRawRepository;

    @GetMapping
    public Iterable<BalanceStatRaw> getRawBalanceSheets() {

        return balanceStatRawRepository.findAll();
    }

    @GetMapping("/{id}")
    public BalanceStatRaw getRawIncomeStatement(@PathVariable final Long id) {

        return balanceStatRawRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find company by id: " + id));
    }
}
