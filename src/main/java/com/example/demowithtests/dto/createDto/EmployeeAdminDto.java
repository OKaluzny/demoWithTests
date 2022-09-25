package com.example.demowithtests.dto.createDto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeAdminDto {

    public String name;
    public String country;
    public String email;
    public Integer age;
    public Boolean isAdult;
    public Boolean isDeleted;
}
