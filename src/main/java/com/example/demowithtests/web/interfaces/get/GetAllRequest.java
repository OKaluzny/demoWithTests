package com.example.demowithtests.web.interfaces.get;

import com.example.demowithtests.dto.readDto.EmployeeReadAllDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

public interface GetAllRequest {
    @Operation(summary = "This is endpoint returned all employees.", description = "Create request to read a employees ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. pam pam param."),
            @ApiResponse(responseCode = "204", description = "There are no employees in database"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found.")})

    List<EmployeeReadAllDto> getAllEmployeeDto();

}
