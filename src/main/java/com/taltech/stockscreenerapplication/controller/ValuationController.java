package com.taltech.stockscreenerapplication.controller;

import com.taltech.stockscreenerapplication.model.Valuation;
import com.taltech.stockscreenerapplication.repository.ValuationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/valuations")
public class ValuationController {

    @Autowired
    ValuationRepository valuationRepository;

    @GetMapping
    public Iterable<Valuation> getValuations() {

        return valuationRepository.findAll();
    }
}
