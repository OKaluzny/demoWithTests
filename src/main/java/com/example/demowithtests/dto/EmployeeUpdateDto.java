package com.example.demowithtests.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeUpdateDto {

    private String name;
    private Integer phoneNumber;
    private String country;
    private String email;
}
