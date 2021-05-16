package com.taltech.stockscreenerapplication.model.statement.attribute;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "attributes")
public class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "attr_id")
    private Long attr_id;

    @Column(name = "field_name")
    private String fieldName; // aka attribute

    @Column(name = "value")
    private double value;

    public Attribute(String fieldName, double value) {
        this.fieldName = fieldName;
        this.value = value;
    }

    public Attribute() {}

    @Override
    public String toString() {
        return "Attribute{" +
                "attr_id=" + attr_id +
                ", fieldName='" + fieldName + '\'' +
                ", value=" + value +
                '}';
    }
}
