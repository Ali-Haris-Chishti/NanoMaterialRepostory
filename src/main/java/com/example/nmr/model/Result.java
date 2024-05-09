package com.example.nmr.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Blob;

@Entity
@Data
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int resultId;

    String dataSet;

    @OneToOne
    @JoinColumn(name = "exp_id", referencedColumnName = "expId")
    Experiment experiment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
