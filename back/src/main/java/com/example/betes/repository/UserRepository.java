package com.example.betes.repository;

import com.example.betes.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long > {

    Page<User> findAllByOrderByUsernameAsc(Pageable pageable);
}
