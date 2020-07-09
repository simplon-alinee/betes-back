package com.example.betes.controller;

import com.example.betes.model.Score;
import com.example.betes.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/score")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;


    @GetMapping("/{id}")
    public Score getById(@PathVariable("id") Long id) {
        return scoreService.getById(id);
    }
}
