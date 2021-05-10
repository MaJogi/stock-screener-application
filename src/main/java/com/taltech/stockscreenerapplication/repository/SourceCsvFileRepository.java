package com.taltech.stockscreenerapplication.repository;

import com.taltech.stockscreenerapplication.model.statement.sourceFile.SourceCsvFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SourceCsvFileRepository extends JpaRepository<SourceCsvFile, Long> {

    Boolean existsBySourceFileName(String fileName);
}

