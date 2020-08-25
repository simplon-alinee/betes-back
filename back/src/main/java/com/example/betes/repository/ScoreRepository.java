package com.example.betes.repository;

import com.example.betes.model.Score;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ScoreRepository extends PagingAndSortingRepository<Score, Long > {

    Page<Score> findAllByOrderByScoreDesc(Pageable pageable);
}
