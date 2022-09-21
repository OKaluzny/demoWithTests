package com.example.demowithtests.web.interfaces.put;

import com.example.demowithtests.domain.Employee;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface PutRefreshRequest {

    @Operation(summary = "This is endpoint allows to update a employee by Id.", description = "Create request to update a employee by Id.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. pam pam param."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})

    Employee refreshEmployee(@PathVariable("id") Integer id, @RequestBody Employee employee);
}
