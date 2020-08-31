package com.example.betes.service;

import com.example.betes.exception.ResourceAlreadyExistException;
import com.example.betes.exception.ResourceNotFoundException;
import com.example.betes.model.*;
import com.example.betes.repository.*;
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
import javax.xml.crypto.Data;
import java.lang.reflect.Field;
import java.util.*;

@Service
public class BetService {

    public static final int PAGE_SIZE_MIN = 10;
    public static final int PAGE_SIZE_MAX = 100;
    public static final int PAGE_MIN = 0;
    private static final String PAGE_VALID_MESSAGE = "La taille de la page doit être comprise entre 10 et 100";

    @Autowired
    private DataLogRepository dataLogRepository;
    @Autowired
    private BetRepository betRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private MatchesRepository matchesRepository;


    public Bet getById(Long id) {
        Optional<Bet> optBet = betRepository.findById(id);
        if (optBet.isPresent() ) { return optBet.get();} else {throw new ResourceNotFoundException();
        }
    }

    public Page<Bet> findAllBets(
            @Min(message = "Le numéro de page ne peut être inférieur à 0", value = PAGE_MIN)
                    Integer page,
            @Min(value = PAGE_SIZE_MIN, message = PAGE_VALID_MESSAGE)
            @Max(value = PAGE_SIZE_MAX, message = PAGE_VALID_MESSAGE)
                    Integer size,
            String sortProperty,
            Sort.Direction sortDirection
    ) {
        //Vérification de sortProperty
        if(Arrays.stream(Bet.class.getDeclaredFields()).
                map(Field::getName).
                filter(s -> s.equals(sortProperty)).count() != 1){
            throw new IllegalArgumentException("La propriété " + sortProperty + " n'existe pas !");
        }

        Pageable pageable = PageRequest.of(page,size,sortDirection, sortProperty);
        Page<Bet> bets = betRepository.findAllByOrderByDateBetDesc(pageable);
        if(page >= bets.getTotalPages()){
            throw new IllegalArgumentException("Le numéro de page ne peut être supérieur à " + bets.getTotalPages());
        } else if(bets.getTotalElements() == 0){
            throw new EntityNotFoundException("Il n'y a aucun pari dans la base de données");
        }
        return bets;
    }

    public Page<Bet> findAllBetsOfUser(
            Long userID,
            @Min(message = "Le numéro de page ne peut être inférieur à 0", value = PAGE_MIN)
                    Integer page,
            @Min(value = PAGE_SIZE_MIN, message = PAGE_VALID_MESSAGE)
            @Max(value = PAGE_SIZE_MAX, message = PAGE_VALID_MESSAGE)
                    Integer size,
            String sortProperty,
            Sort.Direction sortDirection
    ) throws NotFoundException {

        //Vérification de sortProperty
        if(Arrays.stream(Bet.class.getDeclaredFields()).
                map(Field::getName).
                filter(s -> s.equals(sortProperty)).count() != 1){
            throw new IllegalArgumentException("La propriété " + sortProperty + " n'existe pas !");
        }

        User user = null;
        if (userRepository.findById(userID).isPresent()) {
            user = userRepository.findById(userID).get();
        } else throw new NotFoundException("user inexistant");

        Pageable pageable = PageRequest.of(page,size,sortDirection, sortProperty);
        Page<Bet> bets = betRepository.findAllByUserOrderByDateBetDesc(user, pageable);
        if(page >= bets.getTotalPages()){
            throw new IllegalArgumentException("Le numéro de page ne peut être supérieur à " + bets.getTotalPages());
        } else if(bets.getTotalElements() == 0){
            throw new EntityNotFoundException("Il n'y a aucun pari dans la base de données");
        }
        return bets;
    }
    public Bet createBet(BetSkeleton betSkeleton) {
        Date createDate = new Date();
        User user;
        Team team;
        Matches match;
        if (userRepository.findById(betSkeleton.getUserId()).isPresent()) {
            user = userRepository.findById(betSkeleton.getUserId()).get();
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "utilisateur inexistant");
        if (teamRepository.findById(betSkeleton.getBetOnTeamId()).isPresent()) {
            team = teamRepository.findById(betSkeleton.getBetOnTeamId()).get();
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "team inexistante");
        if (matchesRepository.findById(betSkeleton.getMatchId()).isPresent()) {
            match = matchesRepository.findById(betSkeleton.getMatchId()).get();
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "match inexistant");

//        if (betRepository.existsByBetOnTeamAndUserAndMatchEntity(team, user, match)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ce pari existe déjà");
        if (betRepository.existsByUserAndMatchEntity(user, match)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le joueur à déjà parié sur ce match");

        Bet newBet = new Bet();
        newBet.setBetOnTeam(team);
        newBet.setMatchEntity(match);
        newBet.setUser(user);
        newBet.setResultBet(null);
        newBet.setDateBet(createDate);
        newBet.setDateUpdate(null);

        betRepository.save(newBet);
        return newBet;
    }

    /**
     * Cette méthode prend un match TERMINE en paramètre,
     * va trouver chaque pari lié a ce match, et modifier le resultat
     * @param matches
     */
    public void updateBetResult(Matches matches) {
        Team winningTeam;
        Date updateDate = new Date();
        DataLog dataLog = new DataLog();
        dataLog.setConcernedData("BET");
        dataLog.setGoal("UPDATE BET");
        dataLog.setLastModifDate(updateDate);
        if (matches.getWinner() != null) {
            winningTeam = matches.getWinner();
        } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "match non terminé");
        List<Bet> listBetOfMatches = betRepository.findAllByMatchEntity(matches);
        for (Bet curBet : listBetOfMatches) {
            // si result match est égal au paris fait; puis up le score de 1;
            curBet.setResultBet(curBet.getBetOnTeam().equals(winningTeam));
            curBet.getUser().scoreUp();
            curBet.setDateUpdate(updateDate);
            betRepository.saveAll(listBetOfMatches);
            dataLog.setResult("SUCCESS");
            dataLogRepository.save(dataLog);
        }
    }
}
