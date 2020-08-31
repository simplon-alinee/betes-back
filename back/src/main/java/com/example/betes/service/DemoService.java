package com.example.betes.service;

import com.example.betes.exception.ResourceNotFoundException;
import com.example.betes.model.*;
import com.example.betes.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DemoService {

    BetRepository betRepository;
    @Autowired
    BetService betService;
    @Autowired
    MatchesRepository matchesRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    GameRepository gameRepository;
    @Autowired
    TeamRepository teamRepository;

    public void demoGo(Long matchId) {
        // cette méthode va prendre le matchId passé en paramètre, le chercher et changer son résultat et son statut,
        // avant d'appeller la méthode "updateAllBets"
        //
        Optional<MatchEntity> match = matchesRepository.findById(matchId);
        if (match.isPresent()) {
            match.get().setWinner(match.get().getTeamsParticipating().get(0));
            matchesRepository.save(match.get());
            betService.updateBetResult(match.get());
        } else {
            throw new ResourceNotFoundException();
        }
    }
    public void createDemoUser(){
        User user = new User();
        user.setFirstname("florian");
        user.setLastname("ville");
        user.setUsername("Psyga");
        user.setPassword("12334566");
        user.setBirthDate(new Date());
        user.setInscriptionDate(new Date());
        user.setValidationInscription(true);
        user.setRole("user");
        user.setScore(0L);
        userRepository.save(user);
    }
    public MatchEntity createMatchDemo(){
        MatchEntity newMatch = new MatchEntity();
        List<Team> newListTeam = new ArrayList<>();
        Game game = gameRepository.findGameByIdApiExtEquals(1L); //(league)
        Optional<Team> optionalTeam1 = teamRepository.findById(211L);
        Optional<Team> optionalTeam2 = teamRepository.findById(287L);
        optionalTeam1.ifPresent(newListTeam::add);
        optionalTeam2.ifPresent(newListTeam::add);
        newMatch.setTeamsParticipating(newListTeam);
        newMatch.setGame(game);
        newMatch.setIdApiExt(1);
        System.out.println(newMatch);
        matchesRepository.save(newMatch);
        return newMatch;
    }


}
