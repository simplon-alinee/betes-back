package com.example.betes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class BetesApplication {

	public static void main(String[] args) {
		SpringApplication.run(BetesApplication.class, args);
	}


	public void test (){
		System.out.println("toto");
	}
}
