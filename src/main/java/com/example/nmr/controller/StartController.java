package com.example.nmr.controller;

import com.example.nmr.model.Experiment;
import com.example.nmr.model.NanoMaterial;
import com.example.nmr.model.User;
import com.example.nmr.repository.NanoRepo;
import com.example.nmr.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class StartController {
    UserService userService;
    @Autowired
    void setUserService(UserService service){
        this.userService = service;
    }

    @Autowired
    NanoRepo repo;

    @RequestMapping(value = {"/", "/home", "/login"})
    String getLoginPage(){
        userService.currentUser = new User();

// Add more nano materials as needed...

        return "login";
    }

    @RequestMapping("/a")
    void add(){
        NanoMaterial nanoMaterial1 = new NanoMaterial("Graphene", "C", "Hexagonal lattice", "High electrical conductivity, high mechanical strength");

        NanoMaterial nanoMaterial2 = new NanoMaterial("Silica Nanoparticles", "SiO2", "Amorphous", "Biocompatible, fluorescent properties");

        NanoMaterial nanoMaterial3 = new NanoMaterial("Carbon Nanotubes", "C", "Cylindrical", "High tensile strength, excellent electrical conductivity");

        repo.save(nanoMaterial1);
        repo.save(nanoMaterial2);
        repo.save(nanoMaterial3);
    }

    @RequestMapping("/register")
    String getRegistrationPage(){
        return "register";
    }

    @GetMapping("/add")
    String addUser(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String phone,
            @RequestParam String name,
            @RequestParam String role)
    {
        User user = new User(name, email, phone, password, role);
        userService.addUser(user);
        userService.currentUser = user;
        if (role.equalsIgnoreCase("STUDENT"))
            return "redirect:/user/STUDENT";
        else if (role.equalsIgnoreCase("ACADEMIC_STAFF"))
            return "redirect:/user/ACADEMIC";
        else
            return "redirect:/register";
    }

    @GetMapping("/check")
    String checkCredentials(Model model, @RequestParam String email, @RequestParam String password, @RequestParam String role){
        if (userService.checkCredentials(email, password, role)){
            int id = userService.getIdByEmail();
            model.addAttribute("message", "");
            if (role.equalsIgnoreCase("STUDENT"))
                return "redirect:/user/STUDENT";
            else if (role.equalsIgnoreCase("ACADEMIC_STAFF"))
                return "redirect:/user/ACADEMIC";
            else
                return "redirect:/register";
        }
        model.addAttribute("message", "Invalid Login");
        return "login";
    }

    @Data
    public static class LoginData{
        String email;
        String password;
        String role;
    }
}
