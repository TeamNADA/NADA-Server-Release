package com.nada.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class NadaServerApplication {

	public static final String APPLICATION_LOCATIONS =
		"spring.config.location="
			+ "classpath:application.yml,"
			+ "classpath:env.yml,"
			+ "classpath:aws.yml";

	public static void main(String[] args) {
		new SpringApplicationBuilder(NadaServerApplication.class)
			.properties(APPLICATION_LOCATIONS)
				.run(args);
	}

}
