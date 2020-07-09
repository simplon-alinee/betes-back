package com.example.betes.repository;

import com.example.betes.model.Team;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TeamRepository extends PagingAndSortingRepository<Team, Long > {
}
