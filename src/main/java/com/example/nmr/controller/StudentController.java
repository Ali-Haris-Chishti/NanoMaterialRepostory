package com.example.nmr.controller;

import com.example.nmr.model.Experiment;
import com.example.nmr.service.ExpService;
import com.example.nmr.service.NanoService;
import com.example.nmr.service.StudentService;
import com.example.nmr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;

@Controller
@RequestMapping("/student")
public class StudentController {
    StudentService studentService;
    @Autowired
    void setStudentService(StudentService service){
        this.studentService = service;
    }

    UserService userService;
    @Autowired
    void setUserService(UserService service){
        this.userService = service;
    }

    NanoService nanoService;
    @Autowired
    void setNanoService(NanoService service){
        this.nanoService = service;
    }

    ExpService expService;
    @Autowired
    void setExpService(ExpService service){
        this.expService = service;
    }

    @GetMapping("/dashboard")
    ModelAndView showUserDashboard(Model model){
        model.addAttribute("name", userService.getName());
            return new ModelAndView("student/dashboard");
    }

    @GetMapping("/account")
    ModelAndView showAccountDetails(Model model){
        studentService.setStudentDetails(model);
        return new ModelAndView("student/account_details");
    }


    @PostMapping("/submit-experiment")
    public ModelAndView submitExperimentForm(@RequestParam("materialName") String materialName,
                                             @RequestParam("experimentType") String experimentType,
                                             @RequestParam("parameterType") String parameterType,
                                             @RequestParam("parameterValue") String parameterValue,
                                             @RequestParam(required = false, name = "morphImage") MultipartFile morphImage,
                                             @RequestParam(required = false, name = "diameter") String diameter,
                                             @RequestParam(required = false, name = "length") String length,
                                             @RequestParam(required = false, name = "otherImage") MultipartFile otherImage,
                                             @RequestParam(required = false, name = "conductivity") String conductivity
    ) {
        double dia = 0.0, len = 0.0, con = 0.0;
        if (!length.isEmpty()){
            dia = Double.parseDouble(diameter);
            len = Double.parseDouble(length);
        }
        if (!conductivity.isEmpty())
            con = Double.parseDouble(conductivity);
        MultipartFile image = morphImage;
        if (morphImage.isEmpty())
            image = otherImage;
        String codedImage;
        try {
            if (image.isEmpty())
                codedImage = "";
            else
                codedImage = studentService.uploadImage(image);
            System.out.println("\n\n\n\n\n" + codedImage + "\n\n\n\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Experiment experiment = new Experiment(
                experimentType,
                parameterType,
                Double.parseDouble(parameterValue),
                codedImage,
                dia,
                len,
                con,
                nanoService.getMaterialByName(materialName)
        );
        expService.saveExperiment(experiment);

        System.out.println(experiment);

        return new ModelAndView("redirect:/student/experiments"); // Redirect to another endpoint or Thymeleaf template
    }
    @GetMapping("/submit-exp")
    public ModelAndView showSubmitExperimentForm(Model model) {
        model.addAttribute("subSelected", true);
        nanoService.mapMaterialNames(model);
        return new ModelAndView("student/experiment_form"); // Redirect to another endpoint or Thymeleaf template
    }

    @GetMapping("/experiments")
    ModelAndView showFilteredResults(Model model, @RequestParam(required = false) String nano, @RequestParam(required = false) String type){
        studentService.mapExperiments(model, nano, type);
        nanoService.mapMaterialNames(model);
        return new ModelAndView("student/view_result");
    }
}
