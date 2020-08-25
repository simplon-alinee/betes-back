package com.example.betes.repository;

import com.example.betes.model.Matches;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MatchesRepository extends PagingAndSortingRepository<Matches, Long > {

    Page<Matches> findAllByOrderByDateMatchDesc(Pageable pageable);
}
