package com.example.betes.service;

import com.example.betes.exception.ResourceNotFoundException;
import com.example.betes.model.Score;
import com.example.betes.model.User;
import com.example.betes.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    public Score getById(Long id) {
        Optional<Score> optScore = scoreRepository.findById(id);
        if (optScore.isPresent() ) { return optScore.get();} else {throw new ResourceNotFoundException();
        }
    }
}
