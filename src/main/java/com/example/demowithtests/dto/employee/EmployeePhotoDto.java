package com.example.demowithtests.dto.employee;

import lombok.Builder;

import javax.validation.constraints.NotNull;

@Builder
public record EmployeePhotoDto(@NotNull String photo) {
}
