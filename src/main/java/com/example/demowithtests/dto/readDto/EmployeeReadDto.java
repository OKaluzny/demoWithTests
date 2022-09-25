package com.example.demowithtests.dto.readDto;

import java.time.Instant;
import java.util.Date;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeReadDto {
    public String name;
    public String country;
    public String email;
    public Integer age;
    //add technical field
    public Date date = Date.from(Instant.now());
}
