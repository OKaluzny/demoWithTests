package com.example.demowithtests.dto.updateDto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeUpdateEmailDto {
    public Integer id;
    public String email;

}
