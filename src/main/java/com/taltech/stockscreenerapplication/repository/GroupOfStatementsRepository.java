package com.taltech.stockscreenerapplication.repository;

import com.taltech.stockscreenerapplication.model.statement.GroupOfStatements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupOfStatementsRepository extends JpaRepository<GroupOfStatements, Long> {
}
