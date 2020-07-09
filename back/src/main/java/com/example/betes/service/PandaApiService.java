package com.example.betes.service;

import com.example.betes.model.DataLog;
import com.example.betes.model.Game;
import com.example.betes.model.deserializer.CustomGameDeserializer;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class PandaApiService {

    private String authKey = "HmmhLRHU547K3nJRAY1F4XYiu2SLEr_DmL4ctbeRqEB7YouD4UU";
    private String pandaApibaseUrl  = "https://api.pandascore.co";
//    HttpHeaders headersConsumer = new HttpHeaders("Authorization","Bearer "+ authKey);
    private RestTemplate restTemplate;
    @Autowired
    private GameService gameService;
    @Autowired
    private DataLogService dataLogService;


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

    public void needUpdate(){
        // get app last update time
        DataLog lastUpdateApplog = dataLogService.findFirstByGoalOrderByIdDesc("UPDATE APP");
        System.out.println(lastUpdateApplog.getLastModifDate());
    }
}
