package com.example.betes.service;


import com.example.betes.model.Game;
import com.example.betes.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {
	@Autowired
	private GameRepository gameRepository;

	public void saveListGame(List<Game> listGame){
		this.gameRepository.saveAll(listGame);
	}
}
