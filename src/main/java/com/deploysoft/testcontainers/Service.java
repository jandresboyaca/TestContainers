package com.deploysoft.testcontainers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.UUID;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Slf4j
public class Service {

    private final Repository repository;

    public Mono<String> callExternalAPI() {
        WebClient webClient = WebClient.create();

        Mono<String> result = webClient.get()
                .uri("http://localhost:3030/v1/users")
                .retrieve()
                .bodyToMono(String.class);

        result.subscribeOn(Schedulers.parallel()).subscribe(log::info);

        return result;

    }


    public Trader save() {
        Trader trader = Trader.builder().traderId(UUID.randomUUID().toString()).build();
        return repository.save(trader);
    }

}
