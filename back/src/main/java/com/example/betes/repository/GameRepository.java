package com.example.betes.repository;


import com.example.betes.model.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@CrossOrigin(origins = "http://localhost:4200")
public interface GameRepository extends PagingAndSortingRepository<Game, Long > {
	public boolean existsByIdApiExt(Long idApiExt);

	public Game findGameByIdApiExtEquals(Long idApiExt);

	Page<Game> findAllByOrderByNameAsc(Pageable pageable);

	Game getByNameEquals(String name);
}
