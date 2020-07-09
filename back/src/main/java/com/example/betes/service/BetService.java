package com.example.betes.service;

import com.example.betes.exception.ResourceNotFoundException;
import com.example.betes.model.Bet;
import com.example.betes.repository.BetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BetService {
    @Autowired
    private BetRepository betRepository;

    public Bet getById(Long id) {
        Optional<Bet> optBet = betRepository.findById(id);
        if (optBet.isPresent() ) { return optBet.get();} else {throw new ResourceNotFoundException();
        }
    }
}
