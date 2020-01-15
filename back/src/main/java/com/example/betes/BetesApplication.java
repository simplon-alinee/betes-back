package com.example.betes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BetesApplication {

	public static void main(String[] args) {
		SpringApplication.run(BetesApplication.class, args);
	}

}
