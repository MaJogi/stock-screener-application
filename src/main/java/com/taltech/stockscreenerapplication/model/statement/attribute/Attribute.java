package com.taltech.stockscreenerapplication.model.statement.attribute;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
//@AllArgsConstructor
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

    // Maybe we need to add OneToMany. One attribute, many balancestatraw entities. (15:00 min)

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
