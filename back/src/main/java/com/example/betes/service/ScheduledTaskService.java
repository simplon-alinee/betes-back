package com.example.betes.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/*
* Cette classe va permettre d'effectuer de manière régulière une tache en particulier
* le but, est de récupèrer automatiquement les informations de la base de donnée
* */
@Component
public class ScheduledTaskService {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTaskService.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
//
//    @Scheduled(fixedRate = 5000)
//    public void scheduleTaskWithFixedRate() {
//        logger.info("Fixed Rate Task: Current Time - {}", formatter.format(LocalDateTime.now()));
//    }

}
