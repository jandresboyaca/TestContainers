package com.deploysoft.testcontainers;


import org.springframework.data.repository.CrudRepository;

public interface Repository extends CrudRepository<Trader, String> {
}
