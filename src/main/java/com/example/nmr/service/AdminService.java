package com.example.nmr.service;

import com.example.nmr.model.Experiment;
import com.example.nmr.model.NanoMaterial;
import com.example.nmr.model.Report;
import com.example.nmr.model.User;
import com.example.nmr.repository.ExperimentRepo;
import com.example.nmr.repository.NanoRepo;
import com.example.nmr.repository.ReportRepo;
import com.example.nmr.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    UserRepo userRepo;
    @Autowired
    void setUserRepo(UserRepo repo){
        this.userRepo = repo;
    }

    NanoRepo nanoRepo;
    @Autowired
    void setNanoRepo(NanoRepo repo){
        this.nanoRepo = repo;
    }

    ExperimentRepo experimentRepo;
    @Autowired
    void setExperimentRepo(ExperimentRepo repo){
        this.experimentRepo = repo;
    }

    ReportRepo reportRepo;
    @Autowired
    void setReportRepo(ReportRepo repo){
        this.reportRepo = repo;
    }

    public void addStudents(Model model){
        List<User> users = userRepo.findAllByRole("STUDENT");
        model.addAttribute("users", users);
    }

    public void deleteStudent(int id) {
        Optional<User> user = userRepo.findById(id);
        user.ifPresent(value -> userRepo.delete(value));
    }

    public void mapDataBase(Model model) {
        List<User> users = (List<User>) userRepo.findAll();
        model.addAttribute("users", users);

        List<NanoMaterial> nanoMaterials = (List<NanoMaterial>) nanoRepo.findAll();
        model.addAttribute("nanoMaterials", nanoMaterials);

        List<Experiment> experiments = (List<Experiment>) experimentRepo.findAll();
        model.addAttribute("experiments", experiments);

        List<Report> reports = (List<Report>) reportRepo.findAll();
        model.addAttribute("reports", reports);
    }
}
