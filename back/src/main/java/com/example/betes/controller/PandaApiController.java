package com.example.betes.controller;


import com.example.betes.model.DataLog;
import com.example.betes.model.Game;
import com.example.betes.service.DataLogService;
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
    @Autowired
    private DataLogService dataLogService;

    @GetMapping
    public String pandaApiTest() throws IOException, InterruptedException {
        return pandaApiService.testApiConnection();
    }

    @GetMapping("/allGames")
    private void getListeJeux()  throws IOException, InterruptedException {
        pandaApiService.listeGameApi();
    }

    @GetMapping("/needUpdate")
    private void needUpd()  throws IOException, InterruptedException {
        pandaApiService.needUpdate();
    }
}
