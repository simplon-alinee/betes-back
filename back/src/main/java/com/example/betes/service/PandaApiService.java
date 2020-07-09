package com.example.betes.service;

import com.example.betes.model.DataLog;
import com.example.betes.model.Game;
import com.example.betes.model.Team;
import com.example.betes.model.deserializer.CustomGameDeserializer;
import com.example.betes.model.deserializer.CustomTeamDeserializer;
import com.example.betes.repository.GameRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.crypto.Data;
import java.io.Console;
import java.io.IOException;
import java.net.URI;
import java.util.*;


@Service
public class PandaApiService {

    private String authKey = "HmmhLRHU547K3nJRAY1F4XYiu2SLEr_DmL4ctbeRqEB7YouD4UU";
    private String pandaApibaseUrl  = "https://api.pandascore.co";
//    HttpHeaders headersConsumer = new HttpHeaders("Authorization","Bearer "+ authKey);
    private RestTemplate restTemplate;
    @Autowired
    private GameService gameService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private DataLogService dataLogService;
    @Autowired
    private GameRepository gameRepository;


    public PandaApiService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String testApiConnection() throws IOException, InterruptedException {
        String url = pandaApibaseUrl + "/teams";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        // set custom header
        headers.set("Authorization", "Bearer "+ authKey);

        HttpEntity req = new HttpEntity(headers);
        ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.GET, req, String.class, 1);

        if(response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return null;
        }
    }

    public  void listeGameApi() throws IOException, InterruptedException {
        Date currentDate = new Date();
        DataLog dataLog = new DataLog();
        dataLog.setLastModifDate(currentDate);
        dataLog.setGoal("UPDATE APP");

        String url = pandaApibaseUrl + "/videogames?sort=name";
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        // set custom header
        headers.set("Authorization", "Bearer "+ authKey);
        HttpEntity req = new HttpEntity(headers);
        ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.GET, req, String.class, 1);

        if(response.getStatusCode() == HttpStatus.OK) {
            String json = response.getBody();
            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module =
                    new SimpleModule("customGameDeserializer", new Version(1, 0, 0, null, null, null));
            module.addDeserializer(Game.class, new CustomGameDeserializer());
            mapper.registerModule(module);
            List<Game> gameList = mapper.readValue(json,  new TypeReference<List<Game>>(){});
            this.gameService.saveListGame(gameList);

            dataLog.setResult("SUCCES");
            dataLog.setComments(response.getStatusCode().toString());
            dataLogService.saveDataLog(dataLog);
        } else {
            dataLog.setResult("FAILURE");
            dataLog.setComments(response.getStatusCode().toString());
            dataLogService.saveDataLog(dataLog);
        }
    }

    @Scheduled(fixedDelay=2100000) // fixed delay de 35 minutes . donnée calculée sur: http://mon-convertisseur.fr/convertisseur-temps.php
    public void needUpdate(){
        // get app last update time
        DataLog lastUpdateApplog = dataLogService.findFirstByGoalOrderByIdDesc("UPDATE APP");
        if (lastUpdateApplog != null) System.out.println(lastUpdateApplog.getLastModifDate());
       // https://api.pandascore.co/incidents?since=2020-07-08T15:30:40Z?type=match
    }

    public String listTeams() throws JsonProcessingException {
        System.out.println("listTeams");
        Date currentDate = new Date();
        DataLog dataLog = new DataLog();
        dataLog.setLastModifDate(currentDate);
        dataLog.setGoal("UPDATE APP");
        String url = pandaApibaseUrl + "/teams?sort=name&per_page=100&page=";
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        // set custom header
        headers.set("Authorization", "Bearer "+ authKey);
        HttpEntity req = new HttpEntity(headers);
        StringBuilder jsonTest = new StringBuilder();
        boolean stop = false;
        int pageCount = 1;
        List<Team> teamListTotal = new ArrayList<Team>();


        while (!stop){
            String newUrl = url + pageCount;
            System.out.println(newUrl);
            ResponseEntity<String> response = this.restTemplate.exchange(newUrl, HttpMethod.GET, req, String.class, 1);

            if (Objects.equals(response.getBody(), "[]") || response.getBody()== null || response.getBody().length() == 0) {
                stop = true;
            } else {
//                jsonTest.append(response.getBody());
                String json = response.getBody();
//        ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.GET, req, String.class, 1);
//             response.getBody();
//        if(response.getStatusCode() == HttpStatus.OK) {
//            String json = response.getBody();
//        Optional<Game> gameT = this.gameRepository.findById(1l);
            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module =
                    new SimpleModule("customTeamDeserializer", new Version(1, 0, 0, null, null, null));
            module.addDeserializer(Team.class, new CustomTeamDeserializer());
            mapper.registerModule(module);
            List<Team> teamList = mapper.readValue(json, new TypeReference<List<Team>>(){});
//            this.gameService.saveListGame(gameList);
                teamListTotal.addAll(teamList);
            }
            pageCount++;
        }
            dataLog.setResult("SUCCES");
//            dataLog.setComments(response.getStatusCode().toString());

            // attributing game to teams
            for (Team curTeam : teamListTotal) {
                // check if already in base
                System.out.println(curTeam.getIdApiExt());

                if (curTeam.getIdApiExt() != null ) {
                   Team teamTemp = teamService.getByIdExt(curTeam.getIdApiExt());
                    if (teamTemp != null) {
                        Long idInBase = teamTemp.getId();
                        if (idInBase != null ) curTeam.setId(idInBase);
                    }
                }
                if (curTeam.getGame() != null) {
                    Game realGame = gameService.getByIdExt(curTeam.getGame().getIdApiExt());
                    curTeam.setGame(realGame);
                }

            }

            teamService.saveAll(teamListTotal);
            dataLogService.saveDataLog(dataLog);
//        } else {
//            dataLog.setResult("FAILURE");
//            dataLog.setComments(response.getStatusCode().toString());
//            dataLogService.saveDataLog(dataLog);
//        }
        return teamListTotal.toString();
    }


}
