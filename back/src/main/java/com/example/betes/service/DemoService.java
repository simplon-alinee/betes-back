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
    GameRepository gameRepository;
    @Autowired
    TeamRepository teamRepository;

    public void demoGo(Long matchId) {
        // cette méthode va prendre le matchId passé en paramètre, le chercher et changer son résultat et son statut,
        // avant d'appeller la méthode "updateAllBets"
        //
        Optional<MatchEntity> match = matchesRepository.findById(matchId);
        if (match.isPresent()) {
            betService.updateBetResult(match.get());
        } else {
            throw new ResourceNotFoundException();
        }
    }

    public MatchEntity createMatchDemo(){
        MatchEntity newMatch = new MatchEntity();
        List<Team> newListTeam = new ArrayList<>();
        Game game = gameRepository.findGameByIdApiExtEquals(1L); //(league)
//        newMatch.setDateMatch();
//        Team team1;
//        Team team2 = teamRepository.getById(281L);

        Optional<Team> optionalTeam1 = teamRepository.findById(203L);
        Optional<Team> optionalTeam2 = teamRepository.findById(281L);
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
