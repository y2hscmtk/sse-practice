package com.choi76.sse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // Entity Listener available
public class SseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SseApplication.class, args);
	}

}
