package com.example.demowithtests.dto.passport;

import lombok.Builder;

import javax.validation.constraints.NotNull;

@Builder
public record PassportPhotoDto(@NotNull String photoLink) {
}
