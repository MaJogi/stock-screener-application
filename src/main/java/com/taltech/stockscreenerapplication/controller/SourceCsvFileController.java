package com.taltech.stockscreenerapplication.controller;

import com.taltech.stockscreenerapplication.model.statement.SourceCsvFile;
import com.taltech.stockscreenerapplication.repository.SourceCsvFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/sourceCsvFile")
public class SourceCsvFileController {
    @Autowired
    private SourceCsvFileRepository sourceCsvFileRepository;

    @GetMapping
    public Iterable<SourceCsvFile> getSourceFiles() {
        return sourceCsvFileRepository.findAll();
    }

    @GetMapping("/{id}")
    public SourceCsvFile getSourceFile(@PathVariable final Long id) {

        return sourceCsvFileRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unable to find company by id: " + id));
    }
}


