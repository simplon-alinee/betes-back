package com.example.betes.service;

import com.example.betes.exception.ResourceNotFoundException;
import com.example.betes.model.*;
import com.example.betes.repository.BetRepository;
import com.example.betes.repository.MatchesRepository;
import com.example.betes.repository.TeamRepository;
import com.example.betes.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

@Service
public class DemoService {

    BetRepository betRepository;
    @Autowired
    BetService betService;
    @Autowired
    MatchesRepository matchesRepository;


    public void demoGo(Long matchId) {
        // cette méthode va prendre le matchId passé en paramètre, le chercher et changer son résultat et son statut,
        // avant d'appeller la méthode "updateAllBets"
        //
        Optional<Matches> match = matchesRepository.findById(matchId);
        if (match.isPresent()) {
            betService.updateBetResult(match.get());
        } else {
            throw new ResourceNotFoundException();
        }
    }

}
