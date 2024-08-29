package com.example.nmr.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class NanoMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    int nanoId;

    String name;

    String formula;

    String structure;

    String properties;

    @OneToMany(cascade = CascadeType.ALL)
    List<Experiment> experiments;

    public NanoMaterial(String name, String formula, String structure, String properties) {
        this.name = name;
        this.formula = formula;
        this.structure = structure;
        this.properties = properties;
    }
}
