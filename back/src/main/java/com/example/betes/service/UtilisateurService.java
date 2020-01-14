package com.example.betes.service;

import com.example.betes.exception.ResourceNotFoundException;
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

    public Utilisateur getById(Integer id) {
        Optional<Utilisateur> optUser = utilisateurRepository.findById(id);
        if (optUser.isPresent() ) { return optUser.get();} else {throw new ResourceNotFoundException();
        }
    }


}
