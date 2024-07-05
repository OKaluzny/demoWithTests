package com.example.demowithtests.dto;

import com.example.demowithtests.domain.Gender;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class EmployeeAndAgeDto {

    private Integer id;
    private String name;
    private Integer age;
    private String country;
    private String email;
    private Gender gender;

}
