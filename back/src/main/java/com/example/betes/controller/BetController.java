package com.example.betes.controller;

import com.example.betes.exception.ResourceAlreadyExistException;
import com.example.betes.model.Bet;
import com.example.betes.model.BetSkeleton;
import com.example.betes.model.User;
import com.example.betes.service.BetService;
import javassist.NotFoundException;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bet")
@CrossOrigin(origins = "http://localhost:4200")
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

    @GetMapping("/all/{userID}")
    public Page<Bet> findAllBetsOfUser(
            @PathVariable("userID") Long userID,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sortProperty", defaultValue = "dateBet") String sortProperty,
            @RequestParam(value = "sortDirection", defaultValue = "DESC") Sort.Direction sortDirection) throws NotFoundException {

        return betService.findAllBetsOfUser(userID, page, size, sortProperty, sortDirection);
    }
    @PostMapping("/new")
    @ResponseBody
    @NotFound
    public Bet createBet(@RequestBody BetSkeleton betSkel) {
        System.out.println(betSkel);
        return betService.createBet(betSkel);
    }

}
