package com.example.betes.repository;

import com.example.betes.model.Matches;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MatchesRepository extends PagingAndSortingRepository<Matches, Long > {
}
