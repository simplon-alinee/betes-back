package com.example.betes.service;

import com.example.betes.model.DataLog;
import com.example.betes.model.Game;
import com.example.betes.model.Incident;
import com.example.betes.model.Team;
import com.example.betes.model.deserializer.CustomGameDeserializer;
import com.example.betes.model.deserializer.CustomIncidentDeserializer;
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

    private final String authKey = "HmmhLRHU547K3nJRAY1F4XYiu2SLEr_DmL4ctbeRqEB7YouD4UU";
    private final String pandaApibaseUrl  = "https://api.pandascore.co";
//    HttpHeaders headersConsumer = new HttpHeaders("Authorization","Bearer "+ authKey);

    private HttpHeaders headers = new HttpHeaders();
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
        this.headers = new HttpHeaders();
        this.headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        // set custom header
        this.headers.set("Authorization", "Bearer "+ authKey);
    }

    public String testApiConnection() throws IOException, InterruptedException {
        String url = pandaApibaseUrl + "/teams";


        HttpEntity req = new HttpEntity(this.headers);
        ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.GET, req, String.class, 1);

        if(response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return null;
        }
    }
    /*
	Initialise la base de donnée avec TOUT les jeux
	 */
    public  void listeGameApi() throws IOException, InterruptedException {
        Date currentDate = new Date();
        DataLog dataLog = new DataLog();
        dataLog.setLastModifDate(currentDate);
        dataLog.setGoal("UPDATE APP");
        String url = pandaApibaseUrl + "/videogames?sort=name";
        HttpEntity req = new HttpEntity(this.headers);
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
        } else {
            dataLog.setResult("FAILURE");
        }
        dataLog.setComments(response.getStatusCode().toString());
        dataLogService.saveDataLog(dataLog);
    }


    public List<Team> updateTeam(DataLog dataLog, String url) throws JsonProcessingException {
        HttpEntity req = new HttpEntity(this.headers);
        boolean stop = false;
        int pageCount = 1;
        List<Incident> incidentListTotal = new ArrayList<>();
        List<Team> teamListTotal = new ArrayList<>();
        while (!stop){
            String newUrl = url + pageCount;
            System.out.println(newUrl);
            ResponseEntity<String> response = this.restTemplate.exchange(newUrl, HttpMethod.GET, req, String.class, 1);
            if (response.getStatusCode() == HttpStatus.OK) {
                if (Objects.equals(response.getBody(), "[]") || response.getBody()== null || response.getBody().length() == 0) {
                    stop = true;
                } else {
                    String json = response.getBody();
                    ObjectMapper mapper = new ObjectMapper();
                    SimpleModule module =
                            new SimpleModule("customIncidentDeserializer", new Version(1, 0, 0, null, null, null));
                    module.addDeserializer(Incident.class, new CustomIncidentDeserializer());
                    mapper.registerModule(module);
                    List<Incident> incidentList = mapper.readValue(json, new TypeReference<List<Incident>>(){});
                    incidentListTotal.addAll(incidentList);
                }
                pageCount++;
            } else {
                dataLog.setResult("FAILURE");
                dataLog.setComments(response.getStatusCode().toString());
                dataLogService.saveDataLog(dataLog);
            }
        }
        dataLog.setResult("SUCCES");
        // attributing game to teams
        for (Incident curIncident : incidentListTotal) {
            if (curIncident.getType().equals("team") && curIncident.getTeam()!=null) {
                Team curTeam = curIncident.getTeam();
                // check if already in base
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
                teamListTotal.add(curTeam);
            }

        }
        return teamListTotal;
    }
/*
Initialise la base de donnée avec TOUTES les équipes
 */
    public String listTeams() throws JsonProcessingException {
        Date currentDate = new Date();
        DataLog dataLog = new DataLog();
        dataLog.setLastModifDate(currentDate);
        dataLog.setGoal("UPDATE APP");
        String url = pandaApibaseUrl + "/teams?sort=name&per_page=100&page=";

        List<Team> teamListTotal = this.updateTeam(dataLog, url);
        teamService.saveAll(teamListTotal);
        dataLogService.saveDataLog(dataLog);

        return teamListTotal.toString();
    }


    @Scheduled(fixedDelay=2100000) // fixed delay de 35 minutes . donnée calculée sur: http://mon-convertisseur.fr/convertisseur-temps.php
    public void updateAllDataAtFixedRate() throws JsonProcessingException {
        // get app last update time
        DataLog lastUpdateApplog = dataLogService.findFirstByGoalOrderByIdDesc("UPDATE APP");
        if (lastUpdateApplog != null) {
            System.out.println(lastUpdateApplog.getLastModifDate());
            // https://api.pandascore.co/incidents?since=2020-07-08T15:30:40Z?type=match

            // https://api.pandascore.co/incidents?since=2020-07-08T15:30:40Z&per_page=100&type=team&page=1
            this.updateTeamsSince(lastUpdateApplog.getLastModifDate());
        }
    }

    public void updateTeamsSince(Date date) throws JsonProcessingException {
        // on execute une requete sur l'api, pour retourner les incidents, concernant les jeux
        Date currentDate = new Date();
        DataLog dataLog = new DataLog();
        dataLog.setLastModifDate(currentDate);
        dataLog.setGoal("UPDATE APP");
        dataLog.setComments("teams");
        String url = pandaApibaseUrl + "/incidents?since="+date+"&per_page=100&type=team&page=";
//        incidents?since=2020-07-08T15:30:40Z&per_page=100&type=team&page=1
        HttpEntity req = new HttpEntity(this.headers);
        List<Team> teamListTotal = this.updateTeam(dataLog, url);
        teamService.saveAll(teamListTotal);
        dataLogService.saveDataLog(dataLog);
    }



}
