package com.example.nmr.controller;

import com.example.nmr.model.Experiment;
import com.example.nmr.model.NanoMaterial;
import com.example.nmr.service.ExpService;
import com.example.nmr.service.NanoService;
import com.example.nmr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
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

    @GetMapping("/{role}")
    ModelAndView showUserDashboard(Model model, @PathVariable String role){
        model.addAttribute("name", userService.getName());
        if (role.equalsIgnoreCase("STUDENT")) {
            return new ModelAndView("student_dashboard");
        }
        else if (role.equalsIgnoreCase("ACADEMIC"))
            return new ModelAndView("academic_staff_dashboard");
        else return new ModelAndView("");
    }

    @GetMapping("/account")
    ModelAndView showAccountDetails(Model model){
        userService.setUserDetails(model);
        return new ModelAndView("account_details");
    }


    @GetMapping("/submit-experiment")
    public ModelAndView submitExperimentForm(@RequestParam("materialName") String materialName,
                                             @RequestParam("type") String type,
                                             @RequestParam("parameters") String parameters) {
        Experiment experiment = new Experiment(type, parameters, nanoService.getMaterialByName(materialName));
        expService.saveExperiment(experiment);

        return new ModelAndView("redirect:/user/submit-exp"); // Redirect to another endpoint or Thymeleaf template
    }
    @GetMapping("/submit-exp")
    public ModelAndView showSubmitExperimentForm(Model model) {
        model.addAttribute("subSelected", true);
        nanoService.mapMaterialNames(model);
        return new ModelAndView("experiment_form"); // Redirect to another endpoint or Thymeleaf template
    }

    @GetMapping("/experiments")
    ModelAndView showFilteredResults(Model model, @RequestParam(required = false) String nano, @RequestParam(required = false) String type){
        userService.mapExperiments(model, nano, type);
        nanoService.mapMaterialNames(model);
        return new ModelAndView("view_result");
    }
}
