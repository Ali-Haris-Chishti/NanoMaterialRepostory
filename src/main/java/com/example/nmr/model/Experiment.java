package com.example.nmr.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Experiment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int expId;

    String experimentType;

    String parameterType;

    double parameterValue;
    String imagePath;
    Double diameter;
    Double length;
    Double conductivity;


    @ManyToOne
    @JoinColumn(name = "nano_id")
    NanoMaterial nanoMaterial;

    public Experiment(String type, String parameterType, double parameterValue, String imagePath, Double diameter, Double length, double conductivity, NanoMaterial nanoMaterial) {
        this.experimentType = type;
        this.parameterType = parameterType;
        this.parameterValue = parameterValue;
        this.imagePath = imagePath;
        this.diameter = diameter;
        this.length = length;
        this.conductivity = conductivity;
        this.nanoMaterial = nanoMaterial;
    }


    @Override
    public String toString(){
        return "\n\n\n\n" +
                experimentType + "\n" +
                parameterType + "\n" +
                parameterValue + "\n" +
                imagePath + "\n" +
                diameter + "\n" +
                length + "\n" +
                nanoMaterial + "\n";
    }
}
