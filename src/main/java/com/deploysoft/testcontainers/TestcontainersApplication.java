package com.deploysoft.testcontainers;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class TestcontainersApplication implements CommandLineRunner {

	private final Service service;
	public static void main(String[] args) {
		SpringApplication.run(TestcontainersApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		service.callExternalAPI();
	}
}
