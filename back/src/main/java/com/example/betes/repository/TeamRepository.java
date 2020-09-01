package com.example.betes.repository;

import com.example.betes.model.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends PagingAndSortingRepository<Team, Long > {

	public Long getIdByIdApiExtEquals(Long idExt);

	public Team getTeamByIdApiExt(Long idExt);

	Page<Team> findAllByOrderByTeamName(Pageable pageable);

	Team getById(Long id);
}
