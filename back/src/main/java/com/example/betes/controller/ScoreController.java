package com.example.betes.controller;

import com.example.betes.model.Score;
import com.example.betes.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/score")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;


    @GetMapping("/{id}")
    public Score getById(@PathVariable("id") Long id) {
        return scoreService.getById(id);
    }

    @GetMapping("/all")
    public Page<Score> findAllScores(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sortProperty", defaultValue = "score") String sortProperty,
            @RequestParam(value = "sortDirection", defaultValue = "DESC") Sort.Direction sortDirection) {
        return scoreService.findAllScores(page, size, sortProperty, sortDirection);
    }
}
