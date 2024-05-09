package com.example.nmr.repository;

import com.example.nmr.model.Experiment;
import com.example.nmr.model.NanoMaterial;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ExperimentRepo extends CrudRepository<Experiment, Integer> {
    List<Experiment> findByNanoMaterialAndType(NanoMaterial material, String type);
    List<Experiment> findByNanoMaterial(NanoMaterial material);
    List<Experiment> findByType(String type);
}
