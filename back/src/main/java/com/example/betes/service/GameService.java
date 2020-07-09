package com.example.betes.service;


import com.example.betes.exception.ResourceNotFoundException;
import com.example.betes.model.Game;
import com.example.betes.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {
	@Autowired
	private GameRepository gameRepository;

	public void saveListGame(List<Game> listGame){
		Iterator<Game> iterator = listGame.iterator();
		while (iterator.hasNext()) {
			Game game = iterator.next();
			if (!this.gameRepository.existsByIdApiExt(game.getIdApiExt())) this.gameRepository.save(game);
		}
	}

	public Game getById(Long id) {
		Optional<Game> optGame = gameRepository.findById(id);
		if (optGame.isPresent() ) { return optGame.get();} else {throw new ResourceNotFoundException();
		}
	}
	public Game getByIdExt(Long id) {
		return this.gameRepository.findGameByIdApiExtEquals(id);
	}
}
