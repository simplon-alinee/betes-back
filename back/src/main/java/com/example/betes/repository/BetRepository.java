package com.example.betes.repository;

import com.example.betes.model.Bet;
import com.example.betes.model.Matches;
import com.example.betes.model.Team;
import com.example.betes.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BetRepository extends PagingAndSortingRepository<Bet, Long > {

    Page<Bet> findAllByOrderByDateBetDesc(Pageable pageable);
    boolean existsByBetOnTeamAndUserAndMatchEntity(Team betOnTeam, User user, Matches matchEntity);
//    Bet getByUserAndBetOnTeamAndMatches(User user, Team team, Matches matches);
//Bet getByBetOnTeamAnd(Team betOnTeam);
boolean existsByBetOnTeam(Team team);
    boolean existsByUser(User user);

}
