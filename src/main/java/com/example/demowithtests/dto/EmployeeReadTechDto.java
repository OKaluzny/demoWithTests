package com.example.demowithtests.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeReadTechDto {

    private Integer id;
    private Boolean isDeleted;
    private Boolean isUpdated;
}
