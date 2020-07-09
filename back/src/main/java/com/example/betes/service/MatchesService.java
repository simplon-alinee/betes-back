package com.example.betes.service;

import com.example.betes.exception.ResourceNotFoundException;
import com.example.betes.model.Matches;
import com.example.betes.model.User;
import com.example.betes.repository.MatchesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MatchesService {

    @Autowired
    private MatchesRepository matchesRepository;

    public Matches getById(Long id) {
        Optional<Matches> optMatches = matchesRepository.findById(id);
        if (optMatches.isPresent() ) { return optMatches.get();} else {throw new ResourceNotFoundException();
        }
    }
}
