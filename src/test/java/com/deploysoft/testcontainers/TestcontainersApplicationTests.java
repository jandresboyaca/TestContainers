package com.deploysoft.testcontainers;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@Testcontainers
class TestcontainersApplicationTests {

    @Autowired
    private Service service;

    @Container
    public static GenericContainer<?> container = new GenericContainer<>("deploysoft/mockserver:latest")
            .withExposedPorts(3030);


    @AfterAll
    public static void tearDown() {
        container.stop();
    }

    @Test
    void contextLoads() {
        Mono<String> stringMono = service.callExternalAPI();
        StepVerifier.create(stringMono)
                .expectNext("[{\"id\":1,\"name\":\"John\"},{\"id\":2,\"name\":\"Marry\"}]")
                .verifyComplete();


    }

}
