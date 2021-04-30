package com.taltech.stockscreenerapplication.controller;

import com.taltech.stockscreenerapplication.model.statement.GroupOfStatements;
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
}