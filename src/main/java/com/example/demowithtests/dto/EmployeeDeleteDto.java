package com.example.demowithtests.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.Date;

public record EmployeeDeleteDto(
        @Schema(description = "Id in DB")
        Integer id,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        @Schema(description = "Date response")
        Date deleteDate,

        String message
) {
    public EmployeeDeleteDto(
            Integer id,
            Date deleteDate,
            String message) {
        this.id = id;
        this.deleteDate = deleteDate != null ? deleteDate : Date.from(Instant.now());
        this.message = "User deleted";

    }

}
