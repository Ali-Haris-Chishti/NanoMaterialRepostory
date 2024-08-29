package com.example.nmr.service;

import com.example.nmr.model.CurrentUser;
import com.example.nmr.model.Experiment;
import com.example.nmr.model.User;
import com.example.nmr.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    UserRepo userRepo;
    @Autowired
    void setUserRepo(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    NanoRepo nanoRepo;
    @Autowired
    void setNanoRepo(NanoRepo repo){
        this.nanoRepo = repo;
    }

    ExperimentRepo expRepo;
    @Autowired
    void setExperimentRepo(ExperimentRepo repo){
        this.expRepo = repo;
    }

    ReportRepo reportRepo;
    @Autowired
    void setReportRepo(ReportRepo repo){
        this.reportRepo = repo;
    }

    public void mapExperiments(Model model){
        model.addAttribute("expSelected", true);
        List<Experiment> experiments = (List<Experiment>) expRepo.findAll();
        model.addAttribute("experiments", experiments);
    }

    public void mapExperiments(Model model, String nano, String type){
        List<Experiment> experiments;
        model.addAttribute("expSelected", true);
        if (nano == null || type == null){
            experiments = (List<Experiment>) expRepo.findAll();
            model.addAttribute("experiments", experiments);
            return;
        }

        if (nano.isEmpty()){
            if (type.isEmpty())
                experiments = (List<Experiment>) expRepo.findAll();
            else
                experiments = (List<Experiment>) expRepo.findByExperimentType(type);
        }
        else{
            if (type.isEmpty())
                experiments = (List<Experiment>) expRepo.findByNanoMaterial(nanoRepo.findByName(nano).get());
            else
                experiments = (List<Experiment>) expRepo.findByNanoMaterialAndExperimentType(nanoRepo.findByName(nano).get(), type);
        }
        model.addAttribute("experiments", experiments);
    }


    public void setStudentDetails(Model model) {
        model.addAttribute("user", CurrentUser.get());
        model.addAttribute("accSelected", true);
    }

    public String uploadImage(MultipartFile image) throws IOException {
        String path = new File("src\\main\\resources\\static").getAbsolutePath() + "\\images\\" + image.getOriginalFilename();
        System.out.println(path);
        Files.copy(image.getInputStream(), Path.of(path));
        return "/images/" + image.getOriginalFilename();
    }
}
