package com.example.demowithtests.dto.updateDto;

import javax.validation.constraints.NotNull;

public class EmployeeUpdateIsDeleted {
    public Integer id;

    @NotNull(message = "Field may not be null")
    public Boolean isDeleted;
}
