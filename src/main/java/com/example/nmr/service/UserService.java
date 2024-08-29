package com.example.nmr.service;

import com.example.nmr.model.*;
import com.example.nmr.repository.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    public User currentUser = new User();
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

    public void addUser(User user){
        User user1 = userRepo.save(user);
        System.out.println(user1);
    }

    public boolean checkCredentials(String email, String password, String type){
        Optional<User> user = userRepo.findByEmailAndPasswordAndRole(email, password, type);
        user.ifPresent(value -> currentUser = value);
        System.out.println(currentUser.getName()+ "\n\n\n\n\n");
        return user.isPresent();
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

    public int getIdByEmail() {
        return currentUser.getUserId();
    }

    public String getNameById(int id){
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent())
            return user.get().getName();
        else
            return "user";
    }

    public String getName(){
        return currentUser.getName();
    }

    public void setUserDetails(Model model) {
        model.addAttribute("accSelected", true);
        model.addAttribute("name", currentUser.getName());
        model.addAttribute("email", currentUser.getEmail());
        model.addAttribute("password", currentUser.getPassword());
        model.addAttribute("phone", currentUser.getPhoneNumber());
    }

    public byte[] generatePdf() throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            float initialY = 700; // Initial Y position for the first experiment
            float offsetY = 100; // Offset for each new line

            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, initialY);
            contentStream.showText("Report");

            List<Experiment> experiments = (List<Experiment>) expRepo.findAll();
            for (Experiment experiment : experiments) {
                contentStream.newLineAtOffset(0, -offsetY); // Move to the next line
                contentStream.showText("Experiment ID: " + experiment.getExpId());
                contentStream.newLineAtOffset(0, -offsetY); // Move to the next line
                contentStream.showText("Experiment Type: " + experiment.getExperimentType());
                contentStream.newLineAtOffset(0, -offsetY); // Move to the next line
                contentStream.showText("Material Used: " + experiment.getNanoMaterial().getName());
                contentStream.newLineAtOffset(0, -offsetY); // Move to the next line
                contentStream.showText("Parameter Type: " + experiment.getParameterType());
                contentStream.newLineAtOffset(0, -offsetY); // Move to the next line
                contentStream.showText("Parameter Value: " + experiment.getParameterValue());
                contentStream.newLineAtOffset(0, -offsetY); // Move to the next line
                contentStream.showText("Diameter: " + experiment.getDiameter());
                contentStream.newLineAtOffset(0, -offsetY); // Move to the next line
                contentStream.showText("Length: " + experiment.getLength());
            }

            contentStream.endText();
            contentStream.close();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);
            return outputStream.toByteArray();
        }
    }

    public String saveReport() {
        String name = "report-" + LocalDate.now();
        Report report = new Report(CurrentUser.get(), name + LocalDate.now(), LocalDateTime.now());
        reportRepo.save(report);
        return name + ".pdf";
    }

    public void applyAcademicFilters(Model model, String name, String property, String structure){
        List<NanoMaterial> materials;
        if (model == null || property == null || structure == null){
            materials = (List<NanoMaterial>) nanoRepo.findAll();
            model.addAttribute("nanoMaterials", materials);
            return;
        }
        if (property.isEmpty()){
            if (structure.isEmpty())
                materials = nanoRepo.findAllByNameLike(name);
            else
                materials = nanoRepo.findAllByStructureAndNameLike(structure, name);
        }
        else {
            if (structure.isEmpty())
                materials = nanoRepo.findAllByNameLikeAndPropertiesLike(name, property);
            else
                materials = nanoRepo.findAllByNameLikeAndPropertiesLikeAndStructure(name, property, structure);
        }
        System.out.println(materials);
        model.addAttribute("nanoMaterials", materials);
    }

    public User getUser(int id) {
        Optional<User> user = userRepo.findById(id);
        return user.orElseGet(User::new);
    }
}
