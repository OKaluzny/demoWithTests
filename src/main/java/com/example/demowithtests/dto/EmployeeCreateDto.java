package com.example.demowithtests.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeCreateDto {

    private Integer id;
    private String name;
    private String country;
    private String email;
    private Integer phoneNumber;

}
