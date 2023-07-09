package com.example.demowithtests.dto.employee;

import com.example.demowithtests.util.annotations.dto.CountryRightFormed;

import java.time.Instant;
import java.util.Date;

public record AddressDto(Long id,
                         @CountryRightFormed
                         String country,
                         String city,
                         String street,
                         Date date,
                         Boolean addressHasActive) {

    public AddressDto {
        date = Date.from(Instant.now());
    }
}
