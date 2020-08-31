package com.example.betes.controller;

import com.example.betes.model.*;
import com.example.betes.service.BetService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bet")
public class BetController {

    @Autowired
    private BetService betService;

    @GetMapping("/{id}")
    public Bet getById(@PathVariable("id") Long id) { return betService.getById(id); }

    @GetMapping("/all")
    public Page<Bet> findAllBets(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sortProperty", defaultValue = "dateBet") String sortProperty,
            @RequestParam(value = "sortDirection", defaultValue = "DESC") Sort.Direction sortDirection) {
        return betService.findAllBets(page, size, sortProperty, sortDirection);
    }

    @PostMapping("/new")
    @ResponseBody
    public Bet createBet(@RequestBody BetSkeleton betSkel) throws NotFoundException {
        System.out.println(betSkel);
        Bet newBet = betService.createBet(betSkel);
        return newBet;
    }

}
