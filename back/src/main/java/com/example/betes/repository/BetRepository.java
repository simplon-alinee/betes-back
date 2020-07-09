package com.example.betes.repository;

import com.example.betes.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BetRepository extends PagingAndSortingRepository<User, Long > {
}
