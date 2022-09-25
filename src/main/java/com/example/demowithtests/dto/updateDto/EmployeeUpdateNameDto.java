package com.example.demowithtests.dto.updateDto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeUpdateNameDto {
    public Integer id;
    public String name;

}
