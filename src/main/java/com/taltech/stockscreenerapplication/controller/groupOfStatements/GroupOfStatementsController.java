package com.taltech.stockscreenerapplication.controller.groupOfStatements;

import com.taltech.stockscreenerapplication.model.CompanyDimension;
import com.taltech.stockscreenerapplication.model.statement.groupOfStatements.GroupOfStatements;
import com.taltech.stockscreenerapplication.repository.CompanyDimensionRepository;
import com.taltech.stockscreenerapplication.repository.GroupOfStatementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/groupOfStatements")
public class GroupOfStatementsController {
    @Autowired
    GroupOfStatementsRepository groupOfStatementsRepository;

    @Autowired
    CompanyDimensionRepository companyDimensionRepository;

    @GetMapping
    public Iterable<GroupOfStatements> getGroupOfStatements() {
        return groupOfStatementsRepository.findAll();
    }

    @GetMapping("/{id}")
    public GroupOfStatements getGroupOfStatement(@PathVariable final Long id) {
        return groupOfStatementsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unable to find groupOfStatement with id: " + id));
    }

    @GetMapping("/onlyFullGroups/{ticker_id}")
    public Iterable<GroupOfStatements> getOnlyFullGroupOfStatements(@PathVariable final String ticker_id) {

        CompanyDimension company = findCompanyByIdWithExceptionHelper(ticker_id);

        return groupOfStatementsRepository.findGroupOfStatementsWhereEveryStatementIsPresent(company.getTicker_id()); // where...
    }

    public CompanyDimension findCompanyByIdWithExceptionHelper(String tickerId) {
        return companyDimensionRepository.findById(tickerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unable to find company with tickerId: " + tickerId));
    }
}
