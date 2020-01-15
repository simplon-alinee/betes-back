package com.example.betes.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;


@Service
public class PandaApiService {

    private String authKey = "HmmhLRHU547K3nJRAY1F4XYiu2SLEr_DmL4ctbeRqEB7YouD4UU";
    private String pandaApibaseUrl  = "https://api.pandascore.co";
//    HttpHeaders headersConsumer = new HttpHeaders("Authorization","Bearer "+ authKey);
    private RestTemplate restTemplate;

    public PandaApiService(RestTemplateBuilder restTemplateBuilder) {


        this.restTemplate = restTemplateBuilder.build();
    }

    public String testApiConnection() throws IOException, InterruptedException {
        System.out.println("coucou");
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

//    System.out.println(client.get().uri("teams").exchange().toString());
    }
}
