package com.example.demowithtests.dto.employee;

import lombok.Builder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Builder
public record EmployeeSetWorkPlaceDto(
        @NotNull
        @Positive
        Integer employeeId,
        @NotNull
        @Positive
        Integer workPlaceId) {
}
