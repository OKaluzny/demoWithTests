package com.example.demowithtests.dto.employee;

import lombok.Builder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Builder
public record EmployeePassportDto(
        @Positive
        Integer id,

        @NotNull
        String series,

        @NotNull
        Long number,

        EmployeePhotoDto photo) {}
