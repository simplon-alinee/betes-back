package com.example.betes.controller;


import com.example.betes.model.Game;
import com.example.betes.service.PandaApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/pandaApi")
public class PandaApiController {

    @Autowired
    private PandaApiService pandaApiService;

    @GetMapping
    public String pandaApiTest() throws IOException, InterruptedException {
        return pandaApiService.testApiConnection();
    }

    @GetMapping("/allGames")
    private List<Game> getListeJeux()  throws IOException, InterruptedException {
        return pandaApiService.listeGameApi();
    }
}
