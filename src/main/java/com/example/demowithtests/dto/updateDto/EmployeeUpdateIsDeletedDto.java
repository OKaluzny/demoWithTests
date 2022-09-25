package com.example.demowithtests.dto.updateDto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeUpdateIsDeletedDto {
    public Integer id;
    public Boolean isDeleted;
}
