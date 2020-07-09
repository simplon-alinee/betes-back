package com.example.betes.controller;

import com.example.betes.model.Bet;
import com.example.betes.service.BetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bet")
public class BetController {

    @Autowired
    private BetService betService;

    @GetMapping("/{id}")
    public Bet getById(@PathVariable("id") Long id) {
        return betService.getById(id);
    }
}
