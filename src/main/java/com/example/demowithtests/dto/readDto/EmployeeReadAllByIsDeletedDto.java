package com.example.demowithtests.dto.readDto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeReadAllByIsDeletedDto {

    public Integer id;
    public String name;
    public String country;
    public String email;
    public Integer age;
    public Boolean isDeleted;
}
