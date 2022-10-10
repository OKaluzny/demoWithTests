package com.example.demowithtests.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeReadTechDto {

    public Integer id;
    public Boolean isDeleted;
    public Boolean isUpdated;
}
