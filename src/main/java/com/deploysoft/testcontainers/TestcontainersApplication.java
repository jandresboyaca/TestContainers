package com.deploysoft.testcontainers;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class TestcontainersApplication implements CommandLineRunner {

	private final ReactiveService reactiveService;
	private final BlockingService blockingService;
	private final AssumptionsService assumptionsService;
	public static void main(String[] args) {
		SpringApplication.run(TestcontainersApplication.class, args);
	}

	@Override
	public void run(String... args) {
		blockingService.callExternalAPI();
		//reactiveService.callExternalAPI();
		//assumptionsService.callExternalAPI();
	}
}
