package com.example.betes.service;

import com.example.betes.exception.ResourceNotFoundException;
import com.example.betes.model.Team;
import com.example.betes.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public Team getById(Long id) {
        Optional<Team> optTeam = teamRepository.findById(id);
        if (optTeam.isPresent()) {
            return optTeam.get();
        } else {
            throw new ResourceNotFoundException();
        }
    }
}