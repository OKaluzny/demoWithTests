package com.example.demowithtests.dto.readDto;

import java.time.Instant;
import java.util.Date;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeReadAdminDto {

    public Integer id;
    public String name;
    public String country;
    public String email;
    public Integer age;
    public Boolean isAdult;
    public Boolean isDeleted;

    public Date date = Date.from(Instant.now());
}
