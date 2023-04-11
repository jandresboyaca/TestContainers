package com.deploysoft.testcontainers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.deploysoft.testcontainers.ReactiveService.getWebClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlockingService {

    private final Repository repository;

    public void callExternalAPI() {
        WebClient webClient = getWebClient(false);
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        for (int i = 0; i < 1000; i++) {
            executorService.submit(() -> {
                try {
                    String result = webClient.get()
                            .uri("http://localhost:3030/v1/users")
                            .header("Thread", Thread.currentThread().getName())
                            .retrieve()
                            .bodyToMono(String.class)
                            .block();
                    log.info("Iteration {} en el hilo {}", result, Thread.currentThread().getName());
                } catch (Exception e) {
                    log.error("Error [{}] en el hilo {}", e, Thread.currentThread().getName());
                }

            });
        }
        executorService.shutdown();
    }


}
