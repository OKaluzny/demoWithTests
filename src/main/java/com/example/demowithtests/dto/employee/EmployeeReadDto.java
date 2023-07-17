package com.example.demowithtests.dto.employee;

import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.domain.UsersWorkPlaces;
import com.example.demowithtests.dto.AddressDto;
import com.example.demowithtests.dto.WorkPlaceReadDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Date;
import java.util.Set;

@Builder
public record EmployeeReadDto(
        @NotNull(message = "Name may not be null")
        @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
        @Schema(description = "Name of an employee.", example = "Billy", required = true)
        String name,

        @Email
        @NotNull
        String email,

        String country,
        Gender gender,
        Set<AddressDto> addresses,
        Set<UsersWorkPlaces> usersWorkPlaces,
        Date date
) {

    public EmployeeReadDto {
        date = Date.from(Instant.now());
    }
}
