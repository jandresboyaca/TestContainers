package com.deploysoft.testcontainers;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Trader {

    @Id
    String traderId;

    String firstName;

    String lastName;

    Double temporaryNumber;
}