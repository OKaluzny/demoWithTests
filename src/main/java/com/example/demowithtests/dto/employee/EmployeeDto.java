package com.example.demowithtests.dto.employee;

import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.domain.UsersWorkPlaces;
import com.example.demowithtests.dto.AddressDto;
import com.example.demowithtests.dto.WorkPlaceDto;
import com.example.demowithtests.util.annotations.dto.BlockedEmailDomains;
import com.example.demowithtests.util.annotations.dto.CountryRightFormed;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Set;

@Builder
public record EmployeeDto(
        @Positive
        Integer id,

        @NotNull
        @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
        @Schema(description = "Name of an employee.", example = "Billy", required = true)
        String name,

        @CountryRightFormed
        @Schema(description = "Name of the country.", example = "England", required = true)
        String country,

        @Email
        @NotNull
        @BlockedEmailDomains(endings = {".com1", ".ru", ".su", ".ру", ".рф"})
        @Schema(description = "Email address of an employee.", example = "billys@mail.com", required = true)
        String email,

        Gender gender,
        Instant startDate,
        @Valid
        Set<AddressDto> addresses,

        Set<UsersWorkPlaces> workPlaces
) {

    public EmployeeDto {
        startDate = Instant.now();
    }
}
