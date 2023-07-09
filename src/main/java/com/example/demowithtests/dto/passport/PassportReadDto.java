package com.example.demowithtests.dto.passport;

import lombok.Builder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Builder
public record PassportReadDto(
        @Positive
        Integer id,

        @NotNull
        String series,

        @NotNull
        Long number,
        String uuid,

        LocalDateTime expireDate,
        PassportPhotoDto photo) {
}
