package com.example.betes.service;

import com.example.betes.model.Utilisateur;
import com.example.betes.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;


@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public Optional<Utilisateur> getById(Integer id) {
        return utilisateurRepository.findById(id);
    };


}
