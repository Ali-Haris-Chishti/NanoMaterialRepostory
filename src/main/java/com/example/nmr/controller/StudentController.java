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
import org.springframework.web.servlet.ModelAndView;

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


    @GetMapping("/submit-experiment")
    public ModelAndView submitExperimentForm(@RequestParam("materialName") String materialName,
                                             @RequestParam("type") String type,
                                             @RequestParam("parameters") String parameters) {
        Experiment experiment = new Experiment(type, parameters, nanoService.getMaterialByName(materialName));
        expService.saveExperiment(experiment);

        return new ModelAndView("redirect:/student/submit-exp"); // Redirect to another endpoint or Thymeleaf template
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
