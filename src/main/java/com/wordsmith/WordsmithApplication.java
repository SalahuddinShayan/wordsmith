package com.wordsmith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.github.valb3r.letsencrypthelper.tomcat.TomcatWellKnownLetsEncryptChallengeEndpointConfig;


@SpringBootApplication
@Import(TomcatWellKnownLetsEncryptChallengeEndpointConfig.class)
public class WordsmithApplication {

	public static void main(String[] args) {
		SpringApplication.run(WordsmithApplication.class, args);
	}

}
