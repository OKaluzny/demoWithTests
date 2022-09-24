package com.example.demowithtests.dto.updateDto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;

public class EmployeeUpdateAgeDto {
    public Integer id;

    @NotNull(message = "Age may not be null")
    @Schema(description = "Age of an employee.", example = "28", required = true)
    public Integer age;
}
