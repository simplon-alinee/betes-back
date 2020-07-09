package com.example.betes.repository;

import com.example.betes.model.Bet;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BetRepository extends PagingAndSortingRepository<Bet, Long > {
}
