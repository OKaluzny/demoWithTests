package com.example.demowithtests.dto.updateDto;

import io.swagger.v3.oas.annotations.media.Schema;


public class EmployeeUpdateCountryDto {
    public Integer id;

    @Schema(description = "Name of the country.", example = "England", required = true)
    public String country;

}
