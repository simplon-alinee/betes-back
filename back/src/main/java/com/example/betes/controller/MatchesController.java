package com.example.betes.controller;

import com.example.betes.model.MatchEntity;
import com.example.betes.service.MatchesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/matches")
public class MatchesController {

    @Autowired
    private MatchesService matchesService;

    @GetMapping("/{id}")
    public MatchEntity getById(@PathVariable("id") Long id) {
        return matchesService.getById(id);
    }

    @GetMapping("/all")
    public Page<MatchEntity> findAllMatches(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sortProperty", defaultValue = "dateMatch") String sortProperty,
            @RequestParam(value = "sortDirection", defaultValue = "DESC") Sort.Direction sortDirection) {
        return matchesService.findAllMatches(page, size, sortProperty, sortDirection);
    }

    @GetMapping("/all/{gameId}")
    public Page<MatchEntity> findAllMatchEntityByGameId(
            @PathVariable("gameId") Long gameId,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sortProperty", defaultValue = "dateMatch") String sortProperty,
            @RequestParam(value = "sortDirection", defaultValue = "DESC") Sort.Direction sortDirection ) {
        return matchesService.findAllMatchEntityByGameId(gameId,page, size, sortProperty, sortDirection);
    }


}
