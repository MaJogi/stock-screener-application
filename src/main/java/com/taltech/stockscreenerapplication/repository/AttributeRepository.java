package com.taltech.stockscreenerapplication.repository;

import com.taltech.stockscreenerapplication.model.statement.attribute.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, String> { }
