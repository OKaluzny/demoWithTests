package com.example.demowithtests.dto;

import lombok.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeToReadDto {

    public String name;
    public String country;
    public String email;
    public Long phoneNumber;

    public Set<AddressDto> addresses = new HashSet<>();

    public SalaryDto salary;

    public Instant time = Instant.now();
}
