package com.example.betes.controller;

import com.example.betes.model.Utilisateur;
import com.example.betes.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping("/{id}")
    public void getById(@PathVariable("id") Integer id) {
        System.out.println(utilisateurService.getById(1));
    }
}
