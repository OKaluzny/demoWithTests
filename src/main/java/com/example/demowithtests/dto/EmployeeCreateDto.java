package com.example.demowithtests.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeCreateDto {

    @NotNull(message = "Name may not be null")
    @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
    @Schema(description = "Name of an employee.", example = "Billy", required = true)
    public String name;

    @Schema(description = "Name of the country.", example = "England", required = true)
    public String country;

    @Email
    @Schema(description = "Email address of an employee.", example = "billys@mail.com", required = true)
    public String email;

    @Schema(description = "Phone number of the employee", example = "12345", required = true)
    public Long phoneNumber;

    public Set<AddressDto> addresses = new HashSet<>();

    public SalaryDto salary;

}
