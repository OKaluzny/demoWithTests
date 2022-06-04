package com.example.demowithtests.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EmployeeDto {

    public Integer id;

    @NotNull(message = "Name may not be null")
    @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
    public String name;

    public String country;

    @Email
    @NotNull
    public String email;
}
