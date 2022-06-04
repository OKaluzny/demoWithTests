package com.example.demowithtests.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Date;

public class EmployeeReadDto {

    public String country;

    @Email
    @NotNull
    public String email;

    //todo: dfhgjkdfhg Jira - 5544
    public Date date = Date.from(Instant.now());
}
