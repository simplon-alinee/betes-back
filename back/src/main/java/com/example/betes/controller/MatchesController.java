package com.example.betes.controller;

import com.example.betes.model.Matches;
import com.example.betes.service.MatchesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/matches")
public class MatchesController {

    @Autowired
    private MatchesService matchesService;

    @GetMapping("/{id}")
    public Matches getById(@PathVariable("id") Long id) {
        return matchesService.getById(id);
    }
}
