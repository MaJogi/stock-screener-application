package com.taltech.stockscreenerapplication.controller;

import com.taltech.stockscreenerapplication.model.CompanyDimension;
import com.taltech.stockscreenerapplication.repository.CompanyDimensionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/companies")
public class CompanyDimensionController {

    @Autowired
    private CompanyDimensionRepository companyDimensionRepository;

    @GetMapping
    public Iterable<CompanyDimension> getCompanies() {

        return companyDimensionRepository.findAll();
    }

    @GetMapping("/{tickerId}")
    public CompanyDimension getCompany(@PathVariable final String tickerId) {

        return companyDimensionRepository.findById(tickerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find company by id: " + tickerId));
    }
}
