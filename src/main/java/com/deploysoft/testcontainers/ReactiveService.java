package com.deploysoft.testcontainers;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReactiveService {

    public void callExternalAPI() {
        WebClient webClient = getWebClient(false);
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        for (int i = 0; i < 1000; i++) {
            executorService.submit(() -> {
                try {
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

            });
        }
        executorService.shutdown();

    }

    public static WebClient getWebClient(boolean withThreadPool) {
        if (withThreadPool) {
            return getWebClient();
        }
        return WebClient.create();
    }

    private static WebClient getWebClient() {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup(1000);
        HttpClient httpClient = HttpClient.create().tcpConfiguration(tcpClient -> tcpClient.runOn(eventLoopGroup));
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

}
