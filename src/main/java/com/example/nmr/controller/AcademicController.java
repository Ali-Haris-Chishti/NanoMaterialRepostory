package com.example.nmr.controller;

import com.example.nmr.model.CurrentUser;
import com.example.nmr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@RequestMapping("/academic")
public class AcademicController {
    UserService userService;
    @Autowired
    void setUserService(UserService service){
        this.userService = service;
    }

    @GetMapping("/dashboard")
    ModelAndView showDashboard(Model model){
        model.addAttribute("name", CurrentUser.get().getName());
        return new ModelAndView("academic/dashboard");
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> downloadPdf() throws IOException {
        byte[] pdfContent = userService.generatePdf();

        String name  = userService.saveReport();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", name);
        headers.setContentLength(pdfContent.length);
        return ResponseEntity.ok().headers(headers).body(pdfContent);
    }

    @GetMapping("/filter")
    ModelAndView applyFilters(@RequestParam(required = false) String name,
                              @RequestParam(required = false) String property,
                              @RequestParam(required = false) String structure,
                              Model model){
        userService.applyAcademicFilters(model, name, property, structure);
        return new ModelAndView("academic/advance_search");
    }
}
