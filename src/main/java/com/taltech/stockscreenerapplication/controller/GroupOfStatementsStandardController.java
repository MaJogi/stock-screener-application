package com.taltech.stockscreenerapplication.controller;

import com.taltech.stockscreenerapplication.model.CompanyDimension;
import com.taltech.stockscreenerapplication.model.statement.GroupOfStatementsStandard;
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
    public Iterable<GroupOfStatementsStandard> getGroupsOfStandardStatementsForCompany(@PathVariable final String ticker_id) {

        CompanyDimension company = companyDimensionRepository.findById(ticker_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unable to find company with id: " + ticker_id));;

        return groupOfStandardStatementsRepository.findAllByCompanyDimensionIs(company); // where...
    }

    /*
    // When you have created config files and created standard objects from them
    // you can now create groupOfStandardStatements
    @GetMapping("/createGroupOfStandardStatementsFor/{ticker_id}/basedOnRawGroup/{raw_group_id}")
    public GroupOfStatementsStandard createGroupOfStandardStatements(@PathVariable final Long raw_group_id) {

        GroupOfStatements groupOfStatementsRaw = groupOfStatementsRepository.findById(raw_group_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unable to find rawGroupOfStatement with id: " + raw_group_id));


        return null;
    }

     */
}

