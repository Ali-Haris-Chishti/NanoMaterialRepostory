package com.example.nmr.service;

import com.example.nmr.model.Experiment;
import com.example.nmr.repository.ExperimentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpService {
    ExperimentRepo expRepo;
    @Autowired
    void setUserRepo(ExperimentRepo expRepo){
        this.expRepo = expRepo;
    }
    public void saveExperiment(Experiment experiment){
        expRepo.save(experiment);
    }
}
