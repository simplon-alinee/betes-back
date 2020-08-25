package com.example.betes.controller;

import com.example.betes.model.Bet;
import com.example.betes.model.Game;
import com.example.betes.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping("/{id}")
    public Game getById(@PathVariable("id") Long id) {
        return gameService.getById(id);
    }

    @GetMapping("/all")
    public Page<Game> findAllGames(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sortProperty", defaultValue = "name") String sortProperty,
            @RequestParam(value = "sortDirection", defaultValue = "DESC") Sort.Direction sortDirection) {
        return gameService.findAllBets(page, size, sortProperty, sortDirection);
    }
}
