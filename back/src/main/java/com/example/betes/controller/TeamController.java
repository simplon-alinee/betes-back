package com.example.betes.controller;

import com.example.betes.model.Team;
import com.example.betes.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("/{id}")
    public Team getById(@PathVariable("id") Long id) {
        return teamService.getById(id);
    }

    @GetMapping("/all")
    public Page<Team> findAllTeams(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sortProperty", defaultValue = "teamName") String sortProperty,
            @RequestParam(value = "sortDirection", defaultValue = "DESC") Sort.Direction sortDirection) {
        return teamService.findAllTeams(page, size, sortProperty, sortDirection);
    }
}
