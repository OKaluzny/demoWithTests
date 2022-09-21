package com.example.demowithtests.web.interfaces.get;

import com.example.demowithtests.domain.Employee;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

public interface GetAllRequest {
    @Operation(summary = "This is endpoint returned all employees.", description = "Create request to read a employees ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. pam pam param."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found.")})

    List<Employee> getAllUsers();

}
