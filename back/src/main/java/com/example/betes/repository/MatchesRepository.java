package com.example.betes.repository;

import com.example.betes.model.MatchEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MatchesRepository extends PagingAndSortingRepository<MatchEntity, Long > {

    Page<MatchEntity> findAllByOrderByDateMatchDesc(Pageable pageable);
}
