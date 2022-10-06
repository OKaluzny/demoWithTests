package com.example.demowithtests.dto;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeToReadDto {

    private String name;
    private String country;
    private String email;
    private Long phoneNumber;
    private Instant time = Instant.now();
}
