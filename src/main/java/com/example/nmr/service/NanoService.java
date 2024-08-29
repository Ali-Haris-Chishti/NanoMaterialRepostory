package com.example.nmr.service;

import com.example.nmr.model.NanoMaterial;
import com.example.nmr.repository.NanoRepo;
import com.fasterxml.jackson.annotation.OptBoolean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NanoService {
    NanoRepo nanoRepo;
    @Autowired
    void setNanoRepo(NanoRepo repo){
        this.nanoRepo = repo;
    }

    public List<String> getAllMaterialNames() {
        List<NanoMaterial> materials = (List<NanoMaterial>) nanoRepo.findAll();
        List<String> names = new ArrayList<>();
        for (NanoMaterial material: materials)
            names.add(material.getName());
        return names;
    }

    public NanoMaterial getMaterialByName(String material) {
        Optional<NanoMaterial> material1 =  nanoRepo.findByName(material);
        return material1.orElseGet(NanoMaterial::new);
    }

    public void mapMaterialNames(Model model) {
        List<String> materials = getAllMaterialNames();
        model.addAttribute("materialNames", materials);
    }
}
