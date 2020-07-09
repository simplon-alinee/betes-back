package com.example.betes.controller;

import com.example.betes.model.Team;
import com.example.betes.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("/{id}")
    public Team getById(@PathVariable("id") Long id) {
        return teamService.getById(id);
    }
}
