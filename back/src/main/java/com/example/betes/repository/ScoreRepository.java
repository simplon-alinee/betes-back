package com.example.betes.repository;

import com.example.betes.model.Score;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ScoreRepository extends PagingAndSortingRepository<Score, Long > {
}
