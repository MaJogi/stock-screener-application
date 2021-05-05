package com.taltech.stockscreenerapplication.controller.groupOfStatements;

import com.taltech.stockscreenerapplication.model.CompanyDimension;
import com.taltech.stockscreenerapplication.model.statement.groupOfStatements.GroupOfStatementsStandard;
import com.taltech.stockscreenerapplication.repository.CompanyDimensionRepository;
import com.taltech.stockscreenerapplication.repository.GroupOfStandardStatementsRepository;
import com.taltech.stockscreenerapplication.repository.GroupOfStatementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/groupOfStandardStatements")
public class GroupOfStatementsStandardController {
    @Autowired
    GroupOfStandardStatementsRepository groupOfStandardStatementsRepository;

    @Autowired
    GroupOfStatementsRepository groupOfStatementsRepository;

    @Autowired
    CompanyDimensionRepository companyDimensionRepository;

    @GetMapping
    public Iterable<GroupOfStatementsStandard> getGroupOfStatements() {
        return groupOfStandardStatementsRepository.findAll();
    }

    @GetMapping("/{id}")
    public GroupOfStatementsStandard getGroupOfStatement(@PathVariable final Long id) {
        return groupOfStandardStatementsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unable to find groupOfStandardStatement with id: " + id));
    }

    @GetMapping("/for/{ticker_id}")
    public Iterable<GroupOfStatementsStandard> getGroupsOfStandardStatementsForCompany(
            @PathVariable final String ticker_id) {

        CompanyDimension company = findCompanyByIdWithExceptionHelper(ticker_id);

        return groupOfStandardStatementsRepository.findAllByCompanyDimensionIs(company);
    }

    public CompanyDimension findCompanyByIdWithExceptionHelper(String tickerId) {
        return companyDimensionRepository.findById(tickerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unable to find company with tickerId: " + tickerId));
    }
}

