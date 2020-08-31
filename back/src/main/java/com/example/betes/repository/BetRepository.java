package com.example.betes.repository;

import com.example.betes.model.Bet;
import com.example.betes.model.Matches;
import com.example.betes.model.Team;
import com.example.betes.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BetRepository extends PagingAndSortingRepository<Bet, Long > {

    Page<Bet> findAllByOrderByDateBetDesc(Pageable pageable);

    Page<Bet> findAllByUserOrderByDateBetDesc(User user, Pageable pageable);

    boolean existsByBetOnTeamAndUserAndMatchEntity(Team betOnTeam, User user, Matches matchEntity);

    boolean existsByUserAndMatchEntity(User user, Matches matchEntity);

    List<Bet> findAllByMatchEntity(Matches matchEntity);
}
