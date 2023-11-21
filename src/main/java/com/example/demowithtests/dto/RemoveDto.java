package com.example.demowithtests.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.Date;

public record RemoveDto(

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        @Schema(description = "Date response")
        Date removeDate,

        String message

) {
    public RemoveDto(
            Date removeDate,
            String message
    ) {
        this.removeDate = removeDate != null ? removeDate : Date.from(Instant.now());
        this.message = "All users were deleted";

    }

}