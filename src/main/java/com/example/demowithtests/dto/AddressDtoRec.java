package com.example.demowithtests.dto;

import java.time.Instant;
import java.util.Date;

public record AddressDtoRec(Long id,
                            String country,
                            String city,
                            String street,
                            Date date,
                            Boolean addressHasActive) {

    public AddressDtoRec {
        date = Date.from(Instant.now());
    }
}
