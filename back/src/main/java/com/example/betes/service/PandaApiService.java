package com.example.betes.service;

import com.example.betes.model.*;
import com.example.betes.model.deserializer.CustomGameDeserializer;
import com.example.betes.model.deserializer.CustomIncidentDeserializer;
import com.example.betes.model.deserializer.CustomTeamDeserializer;
import com.example.betes.repository.GameRepository;
import com.example.betes.repository.MatchesRepository;
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
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.xml.crypto.Data;
import java.io.Console;
import java.io.IOException;
import java.net.URI;
import java.time.*;
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
    private MatchesRepository matchesRepository;


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
        dataLog.setConcernedData("GAMES");
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

/**
ALERT this method might crash depending on the state of the server of PandaScore. We had troubles with the page "32"
 crashing, when initiation with the date 2020-01-01", we chose to initiate the data collection in may, because it's
 between two split of league of legends pro season
 */
    public List<Team> updateTeam(DataLog dataLog, String url) throws JsonProcessingException {
        HttpEntity req = new HttpEntity(this.headers);
        boolean stop = false;
        int pageCount = 1;
        List<Incident> incidentListTotal = new ArrayList<>();
        List<Team> teamListTotal = new ArrayList<>();
        while (!stop){
            String newUrl = url + pageCount;
            System.out.println(newUrl);
            ResponseEntity<String> response;
            try {
                response = this.restTemplate.exchange(newUrl, HttpMethod.GET, req, String.class);
            } catch(HttpStatusCodeException e) {
                System.out.println("toto");
                ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
                        .body(e.getResponseBodyAsString());
                throw new InternalError();
            }

//            ResponseEntity<String> response = this.restTemplate.exchange(newUrl, HttpMethod.GET, req, String.class);
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
                if (curTeam.getGame() == null) {
                    Game unsupportedGame = gameService.getUnsupportedGame();
                    curTeam.setGame(unsupportedGame);
                }
                if (curTeam.getTeamName() == null) curTeam.setTeamName("Unsupported");
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
        dataLog.setConcernedData("TEAMS");
        String url = pandaApibaseUrl + "/teams?sort=name&per_page=50&page=";

        List<Team> teamListTotal = this.updateTeam(dataLog, url);
        teamService.saveAll(teamListTotal);
        dataLogService.saveDataLog(dataLog);

        return teamListTotal.toString();
    }


    @Scheduled(fixedDelay=2100000) // fixed delay de 35 minutes . donnée calculée sur: http://mon-convertisseur.fr/convertisseur-temps.php
    public void updateAllDataAtFixedRate() throws IOException, InterruptedException {
        // get app last update time
        Boolean needInit = dataLogService.needInit();
        if (needInit) {
            DataLog initLog = new DataLog();
            initLog.setConcernedData("INIT");
            initLog.setGoal("UPDATE APP");
            initLog.setComments("Initialisation des données");
            initLog.setLastModifDate(new Date());
            dataLogService.saveDataLog(initLog);
            this.listeGameApi();
            this.updateTeamsSince(null);
            // TODO UPDATE LES MATCHS SELON PANDASCORE
//            this.updateMatchesSince(null);
        }
        if (!needInit) {
            DataLog lastUpdateGamesApplog = dataLogService.findFirstByGoalAndConcernedDataOrderByIdDesc("UPDATE APP", "GAMES");
            if (!lastUpdateGamesApplog.getConcernedData().equals("GAMES")) this.listeGameApi();
            DataLog lastUpdateTeamsApplog = dataLogService.findFirstByGoalAndConcernedDataOrderByIdDesc("UPDATE APP", "TEAMS");
            if (!lastUpdateTeamsApplog.getConcernedData().equals("TEAMS")) this.updateTeamsSince(lastUpdateTeamsApplog.getLastModifDate());
            // TODO UPDATE LES MATCHS SELON PANDASCORE
//            DataLog lastUpdateMatchesApplog = dataLogService.findFirstByGoalAndConcernedDataOrderByIdDesc("UPDATE APP", "MATCHES");
//            if (!lastUpdateMatchesApplog.getConcernedData().equals("MATCHES")) this.updateMatchesSince(lastUpdateMatchesApplog.getLastModifDate());
        }
    }

    public void updateTeamsSince(Date date) throws JsonProcessingException {
        // on execute une requete sur l'api, pour retourner les incidents, concernant les jeux
        Date currentDate = new Date();
        DataLog dataLog = new DataLog();
        dataLog.setLastModifDate(currentDate);
        dataLog.setGoal("UPDATE APP");
        dataLog.setConcernedData("TEAMS");
        dataLog.setComments("récup de toutes les teams depuis le " + date);
        String url;
        if (date == null) {
            url = pandaApibaseUrl + "/incidents?since=2020-05-01T01:00:33Z&per_page=50&type=team&page=" ;
        } else {
            url = pandaApibaseUrl + "/incidents?since="+date+"&per_page=50&type=team&page=";
        }
        HttpEntity req = new HttpEntity(this.headers);
        List<Team> teamListTotal = this.updateTeam(dataLog, url);
        teamService.saveAll(teamListTotal);
        dataLogService.saveDataLog(dataLog);
    }

    /**
     * TODO FVI - créer méthode de récup et d'update de tout les matches
     * This method will do two things:
     * get all the Incidents from Pandascore, concerning the matches, since the last update of this type date,
     * and will then create or update the matches in the database.
     * If it's an update, it will trigger the betService "update" method
     * @param date
     * @throws JsonProcessingException
     */
    public void updateMatchesSince(Date date) throws JsonProcessingException {
        // on execute une requete sur l'api, pour retourner les incidents, concernant les jeux
        Date currentDate = new Date();
        DataLog dataLog = new DataLog();
        dataLog.setLastModifDate(currentDate);
        dataLog.setGoal("UPDATE APP");
        dataLog.setConcernedData("MATCHES");
        dataLog.setComments("récup de tout les matches depuis le " + date);
        String url;
        if (date == null) {
            url = pandaApibaseUrl + "/incidents?since=2020-05-01T01:00:33Z&per_page=50&type=match&page=" ;
        } else {
            url = pandaApibaseUrl + "/incidents?since="+date+"&per_page=50&type=match&page=";
        }
        HttpEntity req = new HttpEntity(this.headers);
//        List<Matches> listMatchesTotal = this.updateMatches(dataLog, url);
//        matchesRepository.saveAll(teamMatchesTotal);
        dataLogService.saveDataLog(dataLog);
    }



}
