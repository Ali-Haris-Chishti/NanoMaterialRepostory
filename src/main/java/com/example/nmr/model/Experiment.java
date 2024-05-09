package com.example.nmr.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Experiment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int expId;

    String type;

    String parameters;

    @OneToOne(mappedBy = "experiment", cascade = CascadeType.ALL)
    Result result;

    @ManyToOne
    @JoinColumn(name = "nano_id")
    NanoMaterial nanoMaterial;

    public Experiment(String type, String parameters, NanoMaterial nanoMaterial) {
        this.type = type;
        this.parameters = parameters;
        this.nanoMaterial = nanoMaterial;
    }
}
