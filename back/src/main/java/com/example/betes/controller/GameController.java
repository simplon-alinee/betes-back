package com.example.betes.controller;

import com.example.betes.model.Game;
import com.example.betes.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping("/{id}")
    public Game getById(@PathVariable("id") Long id) {
        return gameService.getById(id);
    }
}
