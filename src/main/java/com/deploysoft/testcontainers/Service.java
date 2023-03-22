package com.deploysoft.testcontainers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Slf4j
public class Service {

    public void callExternalAPI() {
        WebClient webClient = WebClient.create();

        Mono<String> result = webClient.get()
                .uri("http://localhost:3030/v1/users")
                .retrieve()
                .bodyToMono(String.class);

        result.subscribe(log::info);
    }
}
