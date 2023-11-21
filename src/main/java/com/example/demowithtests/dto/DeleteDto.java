package com.example.demowithtests.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.Date;

public record DeleteDto(

        @Schema(description = "Id in DB")
        Integer id,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        @Schema(description = "Date response")
        Date DeleteDate,

        String massage

        ) {
    public DeleteDto(
            Integer id,
            Date DeleteDate,
            String massage) {
        this.id = id;
        this.DeleteDate = DeleteDate != null ? DeleteDate : Date.from(Instant.now());
        this.massage = "Employee removed";

    }

}
