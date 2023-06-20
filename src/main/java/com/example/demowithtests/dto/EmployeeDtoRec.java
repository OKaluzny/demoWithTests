package com.example.demowithtests.dto;

import com.example.demowithtests.domain.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Set;

@Builder
public record EmployeeDtoRec(
        @Positive
        Integer id,

        @NotNull
        @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
        @Schema(description = "Name of an employee.", example = "Billy", required = true)
        String name,

        @Schema(description = "Name of the country.", example = "England", required = true)
        String country,

        @Email
        @NotNull
        @Schema(description = "Email address of an employee.", example = "billys@mail.com", required = true)
        String email,

        Gender gender,
        Instant startDate,
        Set<AddressDtoRec> addresses) {

    public EmployeeDtoRec {
        startDate = Instant.now();
    }
}
