package com.wordsmith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
public class WordsmithApplication {

	public static void main(String[] args) {
		SpringApplication.run(WordsmithApplication.class, args);
	}

}
