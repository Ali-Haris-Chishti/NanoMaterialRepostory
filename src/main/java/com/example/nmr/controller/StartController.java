package com.example.nmr.controller;

import com.example.nmr.model.CurrentUser;
import com.example.nmr.model.NanoMaterial;
import com.example.nmr.model.User;
import com.example.nmr.repository.NanoRepo;
import com.example.nmr.repository.UserRepo;
import com.example.nmr.service.StudentService;
import com.example.nmr.service.UserService;
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
        CurrentUser.set(new User());
        return "login";
    }

    // Sample mapping to add test values to database
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
        CurrentUser.set(user);
        if (role.equalsIgnoreCase("STUDENT"))
            return "redirect:/student/dashboard";
        else if (role.equalsIgnoreCase("ACADEMIC_STAFF"))
            return "redirect:/academic/dashboard";
        else
            return "redirect:/admin/dashboard";
    }

    @GetMapping("/check")
    String checkCredentials(Model model, @RequestParam String email, @RequestParam String password, @RequestParam String role){
        if (userService.checkCredentials(email, password, role)){
            int id = userService.getIdByEmail();
            model.addAttribute("message", "");
            CurrentUser.set(userService.getUser(id));
            if (role.equalsIgnoreCase("STUDENT"))
                return "redirect:/student/dashboard";
            else if (role.equalsIgnoreCase("ACADEMIC_STAFF"))
                return "redirect:/academic/dashboard";
            else
                return "redirect:/admin/dashboard";
        }
        model.addAttribute("message", "Invalid Login");
        return "login";
    }
}
