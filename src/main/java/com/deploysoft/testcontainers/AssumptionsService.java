package com.deploysoft.testcontainers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static com.deploysoft.testcontainers.ReactiveService.getWebClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssumptionsService {

    private final Repository repository;

    public void callExternalAPI() {
        WebClient webClient = getWebClient(false);
        try {
            //String result = webClient.get()
            Mono<String> result = webClient.get()
                    .uri("http://localhost:3030/v1/users")
                    .header("Thread", Thread.currentThread().getName())
                    .retrieve()
                    .bodyToMono(String.class);
            result.subscribeOn(Schedulers.parallel()).subscribe(resulta -> System.out.println("Iteración " + resulta + " en el hilo " + Thread.currentThread().getName()));
            log.info("Iteration {} en el hilo {}", result, Thread.currentThread().getName());
        } catch (Exception e) {
            log.error("Error [{}] en el hilo {}", e, Thread.currentThread().getName());
        }

    }

}
