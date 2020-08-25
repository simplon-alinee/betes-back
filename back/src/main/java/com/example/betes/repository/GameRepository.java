package com.example.betes.repository;

import com.example.betes.model.Bet;
import com.example.betes.model.DataLog;
import com.example.betes.model.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GameRepository extends PagingAndSortingRepository<Game, Long > {
	public boolean existsByIdApiExt(Long idApiExt);

	public Game findGameByIdApiExtEquals(Long idApiExt);

	Page<Game> findAllByOrderByName(Pageable pageable);
}
