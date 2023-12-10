package com.example.demowithtests.dto;

import jakarta.validation.constraints.NotBlank;

public record DocumentDto(@NotBlank String number) {
}
