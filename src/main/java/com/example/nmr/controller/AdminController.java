package com.example.nmr.controller;

import com.example.nmr.model.CurrentUser;
import com.example.nmr.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/admin")
public class AdminController {

    AdminService adminService;
    @Autowired
    public void setAdminService(AdminService service){
        this.adminService = service;
    }

    @GetMapping("/dashboard")
    ModelAndView showDashboard(Model model){
        model.addAttribute("name", CurrentUser.get().getName());
        return new ModelAndView("admin/dashboard");
    }

    @GetMapping("/accounts")
    ModelAndView showAccounts(Model model){
        adminService.addStudents(model);
        model.addAttribute("mgSelected", true);
        return new ModelAndView("admin/accounts");
    }

    @GetMapping("/database")
    ModelAndView showDatabase(Model model){
        model.addAttribute("dbSelected", true);
        adminService.mapDataBase(model);
        return new ModelAndView("admin/database");
    }

    @GetMapping("/delete/{id}")
    ModelAndView deleteUser(Model model, @PathVariable int id){
        model.addAttribute("dbSelected", true);
        adminService.deleteStudent(id);
        return new ModelAndView("redirect:/admin/accounts");
    }

}
