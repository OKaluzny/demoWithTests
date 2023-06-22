package com.example.demowithtests.dto;

import com.example.demowithtests.util.annotations.dto.CountryRightFormed;

import java.time.Instant;
import java.util.Date;

//@Accessors(chain = true)
public class AddressDto {

    public Long id;

    public Boolean addressHasActive = Boolean.TRUE;

    @CountryRightFormed
    public String country;

    public String city;

    public String street;

    //todo: dfhgjkdfhg Jira - 5544
    public Date date = Date.from(Instant.now());
}
